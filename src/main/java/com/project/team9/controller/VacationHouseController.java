package com.project.team9.controller;

import com.project.team9.dto.HouseCardDTO;
import com.project.team9.dto.VacationHouseDTO;
import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.Tag;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.resource.VacationHouse;
import com.project.team9.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="house")
@CrossOrigin("*")
public class VacationHouseController {

    private final VacationHouseService service;
    private final AddressService addressService;
    private final PricelistService pricelistService;
    private final TagService tagService;
    private final ImageService imageService;

    final String STATIC_PATH = "src/main/resources/static/";
    final String STATIC_PATH_TARGET = "target/classes/static/";
    final String IMAGES_PATH = "/images/";

    @Autowired
    public VacationHouseController(VacationHouseService vacationHouseService, AddressService addressService, PricelistService pricelistService, TagService tagService, ImageService imageService) {
        this.service = vacationHouseService;
        this.addressService = addressService;
        this.pricelistService = pricelistService;
        this.tagService = tagService;
        this.imageService = imageService;
    }

    @GetMapping
    public List<VacationHouse> getHouses() {
        return service.getVacationHouses();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VacationHouse getVacationHouse(@PathVariable String id) {
        Long houseId = Long.parseLong(id);
        return service.getVacationHouse(houseId);
    }
    @PostMapping(value = "createHouse")
    public Long addVacationHouseForOwner(VacationHouseDTO house, @RequestParam("fileImage") MultipartFile[] multipartFiles) throws IOException {
        VacationHouse vh = getHouseFromDTO(house);
        service.save(vh);
        List<String> paths = saveImages(vh, multipartFiles);
        List<Image> images = getImages(paths);
        vh.setImages(images);
        service.save(vh);
        return vh.getId();
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

    @PostMapping(value = "updateHouse/{id}")
    public void updateVacationHouse(@PathVariable String id, VacationHouseDTO house, @RequestParam("fileImage") MultipartFile[] multipartFiles) throws IOException {
        VacationHouse originalHouse = service.getVacationHouse(Long.parseLong(id));
        System.out.println(house);
        VacationHouse newVacationHouse = getHouseFromDTO(house);
        updateVacationHouse(originalHouse, newVacationHouse);
        service.save(originalHouse);
        List<String> paths = saveImages(originalHouse, multipartFiles);
        List<Image> images = getImages(paths);
        images.forEach(System.out::println);
        originalHouse.setImages(images);
        service.save(originalHouse);
    }

    private void updateVacationHouse(VacationHouse originalHouse, VacationHouse newVacationHouse) {
        originalHouse.setTitle(newVacationHouse.getTitle());
        originalHouse.setPricelist(newVacationHouse.getPricelist());
        originalHouse.setDescription(newVacationHouse.getDescription());
        originalHouse.setNumberOfRooms(newVacationHouse.getNumberOfRooms());
        originalHouse.setNumberOfBedsPerRoom(newVacationHouse.getNumberOfBedsPerRoom());
        originalHouse.setRulesAndRegulations(newVacationHouse.getRulesAndRegulations());
        originalHouse.setAddress(newVacationHouse.getAddress());
        originalHouse.setAdditionalServices(newVacationHouse.getAdditionalServices());
        originalHouse.setCancellationFee(newVacationHouse.getCancellationFee());
        originalHouse.setImages(newVacationHouse.getImages());
    }

    private List<String> saveImages(VacationHouse vh, MultipartFile[] multipartFiles) throws IOException {
        List<String> paths = new ArrayList<>();
        if (multipartFiles == null) {
            return paths;
        }
        Path path = Paths.get(STATIC_PATH + IMAGES_PATH + vh.getId());
        Path path_target = Paths.get(STATIC_PATH_TARGET + IMAGES_PATH + vh.getId());
        savePicturesOnPath(vh, multipartFiles, paths, path);
        savePicturesOnPath(vh, multipartFiles, paths, path_target);
        if (vh.getImages() != null && vh.getImages().size() > 0){
            for (Image image : vh.getImages()) {
                paths.add(image.getPath());
            }
        }
        return paths.stream().distinct().collect(Collectors.toList());
    }

    private void savePicturesOnPath(VacationHouse vh, MultipartFile[] multipartFiles, List<String> paths, Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        for (MultipartFile mpf : multipartFiles) {
            String fileName = mpf.getOriginalFilename();
            try (InputStream inputStream = mpf.getInputStream()) {
                Path filePath = path.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                paths.add(IMAGES_PATH + vh.getId() + "/" + fileName);
            } catch (DirectoryNotEmptyException dnee) {
                continue;
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }
    }

    private VacationHouse getHouseFromDTO(VacationHouseDTO house) {
        Pricelist pl = new Pricelist(house.getPrice(), new Date());
        pricelistService.save(pl);
        int numberOfBedsPerRoom = house.getCapacity() / house.getNumberOfRooms();
        Address adr = new Address(house.getCity(), house.getNumber(), house.getStreet(), house.getCountry());
        addressService.save(adr);
        VacationHouse vh = new VacationHouse(house.getName(), adr, house.getDescription(), house.getRulesAndRegulations(), pl, house.getCancellationFee(), null, house.getNumberOfRooms(), numberOfBedsPerRoom);
        List<Tag> tags = new ArrayList<Tag>();
        for (String tagText : house.getTagsText()){
            Tag tag = new Tag(tagText);
            tagService.save(tag);
            tags.add(tag);
        }
        vh.setAdditionalServices(tags);
        List<Image> images = new ArrayList<Image>();
        for (String path : house.getImagePaths()) {
            Optional<Image> optImage = imageService.getImageByPath(path);
            optImage.ifPresent(images::add);
        }
        vh.setImages(images);
        return vh;
    }

    @GetMapping(value = "houseprof/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VacationHouseDTO getVacationHouseDTO(@PathVariable String id) {
        Long houseId = Long.parseLong(id);
        return service.getVacationHouseDTO(houseId);
    }

    @GetMapping(value = "getownerhouses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HouseCardDTO> getOwnerHouses(@PathVariable String id) {
        Long owner_id = Long.parseLong(id);
        return service.getOwnerHouses(owner_id);
    }

    @GetMapping(value = "getownerhouse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HouseCardDTO getOwnerHouse(@PathVariable String id) {
        return service.getVacationHouseCard(Long.parseLong(id));
    }

    @PutMapping("/{id}")
    public VacationHouse updateVacationHouse(@PathVariable String id, @RequestBody VacationHouse house) {
        Long houseId = Long.parseLong(id);
        VacationHouse vacationHouse = service.getVacationHouse(houseId);
        vacationHouse.setTitle(house.getTitle());
        vacationHouse.setPricelist(house.getPricelist());
        vacationHouse.setDescription(house.getDescription());
        vacationHouse.setNumberOfRooms(house.getNumberOfRooms());
        vacationHouse.setNumberOfBedsPerRoom(house.getNumberOfBedsPerRoom());
        vacationHouse.setRulesAndRegulations(house.getRulesAndRegulations());
        vacationHouse.setAddress(house.getAddress());
        vacationHouse.setAdditionalServices(house.getAdditionalServices());
        vacationHouse.setImages(house.getImages());
        vacationHouse = service.save(house);
        return vacationHouse;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteVacationHouse(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}