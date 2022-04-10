package com.project.team9.service;

import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.repo.FishingInstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FishingInstructorService {

    private final FishingInstructorRepository repository;

    @Autowired
    public FishingInstructorService(FishingInstructorRepository repository) {
        this.repository = repository;
    }

    public List<FishingInstructor> getFishingInstructors() {
        return repository.findAll();

    }
}
