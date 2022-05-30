package com.project.team9.controller;

import com.project.team9.exceptions.UserNotFoundException;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.service.FishingInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "fishinginstructor")
public class FishingInstructorController {

    private final FishingInstructorService service;

    @Autowired
    public FishingInstructorController(FishingInstructorService service) {
        this.service = service;
    }

    @GetMapping
    public List<FishingInstructor> getFishingInstructors() {
        return service.getFishingInstructors();
    }

    @PostMapping(value = "changeProfilePicture/{id}")
    public Boolean changeProfilePicture(@PathVariable String id, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        return service.changeProfilePicture(id, multipartFile);
    }

    @GetMapping("names")
    public ResponseEntity<List<String>> getFishingInstructorNames(){return ResponseEntity.ok(service.getFINames());}

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FishingInstructor getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PutMapping("/{id}/edit")
    FishingInstructor editFishingInstructor(@RequestBody FishingInstructor newFishingInstructor, @PathVariable String id) {
        try {
            return service.editFishingInstructor(newFishingInstructor, id);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return newFishingInstructor;
    }

    @PutMapping("/{id}/edit/password")
    FishingInstructor editFishingInstructor(@RequestBody String newPassword, @PathVariable String id) {
        try {
            return service.editFishingInstructorPassword(newPassword, id);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
