package com.project.team9.service;

import com.project.team9.dto.BoatCardDTO;
import com.project.team9.dto.BoatDTO;
import com.project.team9.dto.HouseCardDTO;
import com.project.team9.dto.VacationHouseDTO;
import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.Tag;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.reservation.BoatReservation;
import com.project.team9.model.resource.Boat;
import com.project.team9.model.resource.VacationHouse;
import com.project.team9.repo.BoatRepository;
import com.project.team9.repo.VacationHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoatService {

    private final BoatRepository repository;
    private final AddressService addressService;
    private final PricelistService pricelistService;
    private final TagService tagService;
    private final ImageService imageService;

    final String STATIC_PATH = "src/main/resources/static/";
    final String STATIC_PATH_TARGET = "target/classes/static/";
    final String IMAGES_PATH = "/images/boats/";

    @Autowired
    public BoatService(BoatRepository repository, AddressService addressService, PricelistService pricelistService, TagService tagService, ImageService imageService) {
        this.repository = repository;
        this.addressService = addressService;
        this.pricelistService = pricelistService;
        this.tagService = tagService;
        this.imageService = imageService;
    }

    public List<Boat> getBoats() {
        return repository.findAll();
    }

    public BoatCardDTO getBoatCard(Long id){
        Boat boat = getBoat(id);
        String address = boat.getAddress().getStreet() + " " + boat.getAddress().getNumber() + ", " + boat.getAddress().getPlace() + ", " + boat.getAddress().getCountry();
        return new BoatCardDTO(boat.getId(), boat.getImages().get(0).getPath(), boat.getTitle(), boat.getDescription(), address);
    }

    public List<BoatCardDTO> getOwnerBoats(Long owner_id) {
        List<Boat> boats = repository.findByOwnerId(owner_id);
        List<BoatCardDTO> boatCards = new ArrayList<BoatCardDTO>();
        for(Boat boat : boats){
            String address = boat.getAddress().getStreet() + " " + boat.getAddress().getNumber() + ", " + boat.getAddress().getPlace() + ", " + boat.getAddress().getCountry();
            String thumbnail = "./images/housenotext.png";
            if (boat.getImages().size() > 0){
                thumbnail = boat.getImages().get(0).getPath();
            }
            boatCards.add(new BoatCardDTO(boat.getId(), thumbnail, boat.getTitle(), boat.getDescription(), address));
        }
        return boatCards;
    }

    public Boat getBoat(Long id) {
        return repository.getById(id);
    }

    public BoatDTO getBoatDTO(Long id) {
        Boat bt = repository.getById(id);
        String address = bt.getAddress().getStreet() + " " + bt.getAddress().getNumber() + ", " + bt.getAddress().getPlace()  + ", " + bt.getAddress().getCountry();
        List<String> images = new ArrayList<String>();
        for (Image img : bt.getImages()) {
            images.add(img.getPath());
        }
        return new BoatDTO(bt.getId(), bt.getTitle(), address, bt.getAddress().getNumber(), bt.getAddress().getStreet(), bt.getAddress().getPlace(), bt.getAddress().getCountry(), bt.getDescription(), bt.getType(), images, bt.getRulesAndRegulations(), bt.getEngineNumber(), bt.getEngineStrength(), bt.getTopSpeed(), bt.getLength(), bt.getNavigationEquipment(), bt.getFishingEquipment(), bt.getAdditionalServices(), bt.getPricelist().getPrice(), bt.getCancellationFee(), bt.getCapacity(), bt.getQuickReservations());
    }

    public void addBoat(Boat boat) {
        repository.save(boat);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Long createBoat(BoatDTO boat, MultipartFile[] multipartFiles) throws IOException {
        Boat bt = getBoatFromDTO(boat);
        this.addBoat(bt);
        List<String> paths = saveImages(bt, multipartFiles);
        List<Image> images = getImages(paths);
        bt.setImages(images);
        this.addBoat(bt);
        return bt.getId();
    }


    public BoatDTO updateBoat(String id, BoatDTO boatDTO, MultipartFile[] multipartFiles) throws IOException {
        Boat originalBoat = this.getBoat(Long.parseLong(id));
        Boat newBoat = getBoatFromDTO(boatDTO);
        updateBoatFromNew(originalBoat, newBoat);
        this.addBoat(originalBoat);
        List<String> paths = saveImages(originalBoat, multipartFiles);
        List<Image> images = getImages(paths);
        originalBoat.setImages(images);
        this.addBoat(originalBoat);
        return this.getBoatDTO(originalBoat.getId());
    }

    private List<Image> getImages(List<String> paths) {
        List<Image> images = new ArrayList<Image>();
        for (String path : paths) {
            Optional<Image> optImg = imageService.getImageByPath(path);
            Image img;
            img = optImg.orElseGet(() -> new Image(path));
            imageService.save(img);
            images.add(img);
        }
        return images;
    }

    private void updateBoatFromNew(Boat originalBoat, Boat newBoat) {
        originalBoat.setTitle(newBoat.getTitle());
        originalBoat.setPricelist(newBoat.getPricelist());
        originalBoat.setDescription(newBoat.getDescription());
        originalBoat.setType(newBoat.getType());
        originalBoat.setLength(newBoat.getLength());
        originalBoat.setTopSpeed(newBoat.getTopSpeed());
        originalBoat.setEngineNumber(newBoat.getEngineNumber());
        originalBoat.setEngineStrength(newBoat.getEngineStrength());
        originalBoat.setRulesAndRegulations(newBoat.getRulesAndRegulations());
        originalBoat.setAddress(newBoat.getAddress());
        originalBoat.setNavigationEquipment(newBoat.getNavigationEquipment());
        originalBoat.setFishingEquipment(newBoat.getFishingEquipment());
        originalBoat.setAdditionalServices(newBoat.getAdditionalServices());
        originalBoat.setCancellationFee(newBoat.getCancellationFee());
        originalBoat.setCapacity(newBoat.getCapacity());
        originalBoat.setImages(newBoat.getImages());
    }

    private List<String> saveImages(Boat boat, MultipartFile[] multipartFiles) throws IOException {
        List<String> paths = new ArrayList<>();
        if (multipartFiles == null) {
            return paths;
        }
        Path path = Paths.get(STATIC_PATH + IMAGES_PATH + boat.getId());
        Path path_target = Paths.get(STATIC_PATH_TARGET + IMAGES_PATH + boat.getId());
        savePicturesOnPath(boat, multipartFiles, paths, path);
        savePicturesOnPath(boat, multipartFiles, paths, path_target);
        if (boat.getImages() != null && boat.getImages().size() > 0){
            for (Image image : boat.getImages()) {
                paths.add(image.getPath());
            }
        }
        return paths.stream().distinct().collect(Collectors.toList());
    }

    private void savePicturesOnPath(Boat boat, MultipartFile[] multipartFiles, List<String> paths, Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        for (MultipartFile mpf : multipartFiles) {
            String fileName = mpf.getOriginalFilename();
            try (InputStream inputStream = mpf.getInputStream()) {
                Path filePath = path.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                paths.add(IMAGES_PATH + boat.getId() + "/" + fileName);
            } catch (DirectoryNotEmptyException dnee) {
                continue;
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }
    }

    private Boat getBoatFromDTO(BoatDTO boatDTO) {
        Pricelist pl = new Pricelist(boatDTO.getPrice(), new Date());
        pricelistService.addPriceList(pl);
        Address adr = new Address(boatDTO.getCity(), boatDTO.getNumber(), boatDTO.getStreet(), boatDTO.getCountry());
        addressService.addAddress(adr);
        Boat boat = new Boat(boatDTO.getName(), adr, boatDTO.getDescription(),  boatDTO.getRulesAndRegulations(), pl, boatDTO.getCancellationFee(), null, boatDTO.getType(), boatDTO.getLength(), boatDTO.getEngineNumber(), boatDTO.getEngineStrength() ,boatDTO.getTopSpeed(),boatDTO.getCapacity());

        List<Tag> tags = new ArrayList<Tag>();
        for (String tagText : boatDTO.getTagsText()){
            Tag tag = new Tag(tagText);
            tagService.addTag(tag);
            tags.add(tag);
        }
        boat.setNavigationEquipment(tags);

        List<Tag> tagsFishingEquip = new ArrayList<Tag>();
        for (String tagText : boatDTO.getTagsFishingEquipText()){
            Tag tag = new Tag(tagText);
            tagService.addTag(tag);
            tagsFishingEquip.add(tag);
        }
        boat.setFishingEquipment(tagsFishingEquip);

        List<Tag> tagsAdditionalServices = new ArrayList<Tag>();
        for (String tagText : boatDTO.getTagsAdditionalServicesText()){
            Tag tag = new Tag(tagText);
            tagService.addTag(tag);
            tagsAdditionalServices.add(tag);
        }
        boat.setAdditionalServices(tagsAdditionalServices);

        List<Image> images = new ArrayList<Image>();
        if (boatDTO.getImagePaths() != null){
            for (String path : boatDTO.getImagePaths()) {
                Optional<Image> optImage = imageService.getImageByPath(path);
                optImage.ifPresent(images::add);
            }
        }
        boat.setImages(images);
        return boat;
    }
}