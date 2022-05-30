package com.project.team9.service;

import com.project.team9.dto.UpdateOwnerDTO;
import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.resource.Boat;
import com.project.team9.model.resource.VacationHouse;
import com.project.team9.model.user.User;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.repo.VacationHouseOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class VacationHouseOwnerService {

    private final VacationHouseOwnerRepository repository;
    private final AddressService addressService;
    private final ImageService imageService;

    final String STATIC_PATH = "src/main/resources/static/";
    final String STATIC_PATH_TARGET = "target/classes/static/";
    final String IMAGES_PATH = "/images/houseOwners/";

    @Autowired
    public VacationHouseOwnerService(VacationHouseOwnerRepository vacationHouseOwnerRepository, AddressService addressService, ImageService imageService) {
        this.repository = vacationHouseOwnerRepository;
        this.addressService = addressService;
        this.imageService = imageService;
    }

    public VacationHouseOwner getOwner(Long id) {
        return repository.getById(id);
    }

    public void updateOwner(Long id, UpdateOwnerDTO newOwner){
        VacationHouseOwner oldOwner = this.getOwner(id);
        updateOwner(oldOwner, newOwner);
    }
    private void updateOwner(VacationHouseOwner oldOwner, UpdateOwnerDTO newOwner){
        oldOwner.setFirstName(newOwner.getFirstName());
        oldOwner.setLastName(newOwner.getLastName());
        oldOwner.setPhoneNumber(newOwner.getPhoneNumber());
        Address oldAdr = oldOwner.getAddress();
        oldAdr.setStreet(newOwner.getStreet());
        oldAdr.setNumber(newOwner.getNumber());
        oldAdr.setPlace(newOwner.getPlace());
        oldAdr.setCountry(newOwner.getCountry());
        addressService.addAddress(oldAdr);
        this.addOwner(oldOwner);
    }
    public void addOwner(VacationHouseOwner owner) {
        repository.save(owner);
    }

    public Boolean checkPassword(Long id, String oldPassword){
        VacationHouseOwner owner = this.getOwner(id);
        return owner.getPassword().equals(oldPassword);
    }

    public void updatePassword(Long id, String newPassword){
        VacationHouseOwner owner = this.getOwner(id);
        owner.setPassword(newPassword);
        this.addOwner(owner);
    }

    public Boolean changeProfilePicture(Long id, MultipartFile multipartFile) throws IOException {
        try{
            VacationHouseOwner owner = this.getOwner(id);
            String path = saveImage(owner, multipartFile);
            Image image = getImage(path);
            owner.setProfileImg(image);
            this.save(owner);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private Image getImage(String path) {
        Image image;
        Optional<Image> optImg = imageService.getImageByPath(path);
        image = optImg.orElseGet(() -> new Image(path));
        imageService.save(image);
        return image;
    }

    private String saveImage(VacationHouseOwner owner, MultipartFile multipartFile) throws IOException {
        String pathStr = "";
        if (multipartFile == null) {
            return pathStr;
        }
        Path path = Paths.get(STATIC_PATH + IMAGES_PATH + owner.getId());
        Path path_target = Paths.get(STATIC_PATH_TARGET + IMAGES_PATH + owner.getId());
        savePictureOnPath(owner, multipartFile, pathStr, path);
        pathStr = savePictureOnPath(owner, multipartFile, pathStr, path_target);
        return pathStr;
    }

    private String savePictureOnPath(VacationHouseOwner owner, MultipartFile mpf, String pathStr, Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        String fileName = mpf.getOriginalFilename();
        try (InputStream inputStream = mpf.getInputStream()) {
            Path filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            pathStr = IMAGES_PATH + owner.getId() + "/" + fileName;
        } catch (DirectoryNotEmptyException dnee) {
            throw new IOException("Directory Not Empty Exception: " + fileName, dnee);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
        return pathStr;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public VacationHouseOwner save(VacationHouseOwner owner) {
        return repository.save(owner);
    }

    public VacationHouseOwner getVacationHouseOwnerByEmail(String username) {
        return repository.findByEmail(username);
    }
    public List<VacationHouseOwner> getAll() {
        return repository.findAll();
    }

    public List<VacationHouseOwner> getUnregisteredVacationHouseOwners() {
        List<VacationHouseOwner> owners=this.getAll();
        List<VacationHouseOwner> filtered=new ArrayList<>();
        for (VacationHouseOwner vacationHouseOwner : owners) {
            if (!vacationHouseOwner.isEnabled() && !vacationHouseOwner.getDeleted())
                filtered.add(vacationHouseOwner);
        }
        return filtered;
    }

    public List<String> getVHONames() {
        List<String> names=new ArrayList<>();
        String fullName="";
        for (VacationHouseOwner vacationHouseOwner :
                repository.findAll()) {
            fullName=vacationHouseOwner.getFirstName()+" "+vacationHouseOwner.getLastName();
            if(!names.contains(fullName))
                names.add(fullName);
        }
        return names;
    }
}