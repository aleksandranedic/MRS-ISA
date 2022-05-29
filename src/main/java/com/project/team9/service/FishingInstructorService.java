package com.project.team9.service;

import com.project.team9.exceptions.UserNotFoundException;
import com.project.team9.model.Image;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.repo.FishingInstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FishingInstructorService {

    final String STATIC_PATH = "src/main/resources/static/";
    final String STATIC_PATH_TARGET = "target/classes/static/";
    final String IMAGES_PATH = "/images/instructors/";

    private final FishingInstructorRepository repository;
    private final ImageService imageService;

    @Autowired
    public FishingInstructorService(FishingInstructorRepository repository, ImageService imageService) {
        this.repository = repository;
        this.imageService = imageService;
    }

    public List<FishingInstructor> getFishingInstructors() {
        return repository.findAll();

    }

    public FishingInstructor getById(String id) {
        return repository.getById(Long.parseLong(id));
    }

    public Boolean changeProfilePicture(String id, MultipartFile multipartFile) throws IOException {
        try{
            FishingInstructor instructor = this.getById(id);
            String path = saveImage(instructor, multipartFile);
            Image image = getImage(path);
            instructor.setProfileImg(image);
            this.addFishingInstructor(instructor);
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

    private String saveImage(FishingInstructor instructor, MultipartFile multipartFile) throws IOException {
        String pathStr = "";
        if (multipartFile == null) {
            return pathStr;
        }
        Path path = Paths.get(STATIC_PATH + IMAGES_PATH + instructor.getId());
        Path path_target = Paths.get(STATIC_PATH_TARGET + IMAGES_PATH + instructor.getId());
        savePictureOnPath(instructor, multipartFile, pathStr, path);
        pathStr = savePictureOnPath(instructor, multipartFile, pathStr, path_target);
        return pathStr;
    }

    private String savePictureOnPath(FishingInstructor instructor, MultipartFile mpf, String pathStr, Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        String fileName = mpf.getOriginalFilename();
        try (InputStream inputStream = mpf.getInputStream()) {
            Path filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            pathStr = IMAGES_PATH + instructor.getId() + "/" + fileName;
        } catch (DirectoryNotEmptyException dnee) {
            throw new IOException("Directory Not Empty Exception: " + fileName, dnee);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
        return pathStr;
    }

    public FishingInstructor editFishingInstructor(FishingInstructor newFishingInstructor, String id) throws UserNotFoundException {

        return repository.findById(Long.parseLong(id)).map(fishingInstructor -> {
            fishingInstructor.setFirstName(newFishingInstructor.getFirstName());
            fishingInstructor.setLastName(newFishingInstructor.getLastName());
            fishingInstructor.setPhoneNumber(newFishingInstructor.getPhoneNumber());

            return repository.save(fishingInstructor);
        }).orElseThrow(() -> new UserNotFoundException(id));
    }

    public FishingInstructor editFishingInstructorPassword(String newPassword, String id) throws UserNotFoundException {

        return repository.findById(Long.parseLong(id)).map(fishingInstructor -> {
            fishingInstructor.setPassword(newPassword);
            return repository.save(fishingInstructor);
        }).orElseThrow(() -> new UserNotFoundException(id));
    }

    public FishingInstructor getFishingInstructorByEmail(String username) {
        return repository.findByEmail(username);
    }

    public FishingInstructor addFishingInstructor(FishingInstructor fishingInstructor) {
        return repository.save(fishingInstructor);
    }

    public List<FishingInstructor> getUnregisteredFishingInstructors() {
        List<FishingInstructor> instructors = this.getFishingInstructors();
        List<FishingInstructor> filtered = new ArrayList<>();
        for (FishingInstructor instructor : instructors) {
            if (!instructor.isEnabled() && !instructor.getDeleted())
                filtered.add(instructor);
        }
        return filtered;
    }

    public List<String> getFINames() {
        List<String> names = new ArrayList<>();
        String fullName = "";
        for (FishingInstructor fishingInstructor :
                repository.findAll()) {
            fullName = fishingInstructor.getFirstName() + " " + fishingInstructor.getLastName();
            if (!names.contains(fullName))
                names.add(fullName);
        }
        return names;
    }
}
