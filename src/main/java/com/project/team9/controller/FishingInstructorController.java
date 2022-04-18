package com.project.team9.controller;

import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.service.FishingInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="fishinginstructor")
@CrossOrigin(origins="http://localhost:3000")
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
}
