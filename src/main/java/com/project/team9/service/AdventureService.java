package com.project.team9.service;

import com.project.team9.dto.AdventureDTO;
import com.project.team9.exceptions.AdventureNotFoundException;
import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.Tag;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.resource.Adventure;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.repo.AdventureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdventureService {
    private final AdventureRepository repository;
    private final FishingInstructorService fishingInstructorService;
    private final TagService tagService;
    private final AddressService addressService;
    private final PricelistService pricelistService;
    private final ImageService imageService;

    final String IMAGES_PATH = "/images/adventures/";

    @Autowired
    public AdventureService(AdventureRepository adventureRepository, FishingInstructorService fishingInstructorService, TagService tagService, AddressService addressService, PricelistService pricelistService, ImageService imageService) {
        this.repository = adventureRepository;
        this.fishingInstructorService = fishingInstructorService;
        this.tagService = tagService;
        this.addressService = addressService;
        this.pricelistService = pricelistService;
        this.imageService = imageService;
    }

    public List<Adventure> getAdventures() {
        return repository.findAll();
    }

    public void addAdventure(Adventure adventure) {
        repository.save(adventure);
    }

    public Adventure getById(String id) {
        return repository.getById(Long.parseLong(id));
    }

    public AdventureDTO getDTOById(String id) {
        return new AdventureDTO(repository.getById(Long.parseLong(id)));
    }

    public void deleteById(String id) {
        repository.deleteById(Long.parseLong(id));
    }

    private void updateAdventure(Adventure oldAdventure, Adventure newAdventure) {
        oldAdventure.setTitle(newAdventure.getTitle());
        oldAdventure.setAddress(newAdventure.getAddress());
        oldAdventure.setDescription(newAdventure.getDescription());
        oldAdventure.setImages(newAdventure.getImages());
        oldAdventure.setAdditionalServices(newAdventure.getAdditionalServices());
        oldAdventure.setRulesAndRegulations(newAdventure.getRulesAndRegulations());
        oldAdventure.setPricelist(newAdventure.getPricelist());
        oldAdventure.setCancellationFee(newAdventure.getCancellationFee());
        oldAdventure.setOwner(newAdventure.getOwner());
        oldAdventure.setNumberOfClients(newAdventure.getNumberOfClients());
        oldAdventure.setFishingEquipment(newAdventure.getFishingEquipment());
    }

    public List<Adventure> findAdventuresWithOwner(String ownerId){
        return repository.findAdventuresWithOwner(Long.parseLong(ownerId));
    }

    public Long createAdventure(AdventureDTO adventure, MultipartFile[] multipartFiles) throws IOException {

        Adventure newAdventure = createAdventureFromDTO(adventure);
        repository.save(newAdventure);
        addImagesToAdventure(multipartFiles, newAdventure);
        return newAdventure.getId();
    }

    private Adventure createAdventureFromDTO(AdventureDTO dto) {
        Pricelist pricelist = new Pricelist(dto.getPrice(), new Date());
        pricelistService.addPriceList(pricelist);

        Address address = new Address(dto.getPlace(), dto.getNumber(),dto.getStreet() , dto.getCountry());
        addressService.addAddress(address);

        FishingInstructor owner = fishingInstructorService.getById(dto.getOwnerId().toString());

        Adventure adventure = new Adventure(
                dto.getTitle(),
                address,
                dto.getDescription(),
                dto.getRulesAndRegulations(),
                pricelist,
                dto.getCancellationFee(),
                owner,
                dto.getNumberOfClients()
        );

        for (String text: dto.getAdditionalServicesText()) {
            Tag tag = new Tag(text);
            tagService.addTag(tag);
            adventure.addAdditionalService(tag);
        }

        for (String text: dto.getFishingEquipmentText()) {
            Tag tag = new Tag(text);
            tagService.addTag(tag);
            adventure.addFishingEquipment(tag);
        }

        List<Image> images = getExistingImages(dto);
        adventure.setImages(images);

        return adventure;
    }

    private List<Image> getExistingImages(AdventureDTO dto) {
        List<Image> images = new ArrayList<Image>();
        if (dto.getImagePaths() != null){
            for (String path : dto.getImagePaths()) {
                Optional<Image> optImage = imageService.getImageByPath(path);
                optImage.ifPresent(images::add);
            }
        }
        return images;
    }

    public Adventure editAdventure(String id, AdventureDTO dto, MultipartFile[] multipartFiles) throws IOException {
        Adventure originalAdventure = getById(id);
        updateAdventure(originalAdventure, createAdventureFromDTO(dto));
        repository.save(originalAdventure);
        addImagesToAdventure(multipartFiles, originalAdventure);
        return originalAdventure;
    }

    private void addImagesToAdventure(MultipartFile[] multipartFiles, Adventure adventure) throws IOException {
        List<String> paths = imageService.saveImages(adventure.getId(), multipartFiles, IMAGES_PATH, adventure.getImages());
        List<Image> images = imageService.getImages(paths);
        adventure.setImages(images);
        repository.save(adventure);
    }
}
