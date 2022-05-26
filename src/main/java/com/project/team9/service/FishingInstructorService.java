package com.project.team9.service;

import com.project.team9.exceptions.UserNotFoundException;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.repo.FishingInstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public FishingInstructor getById(String id) {
        return repository.getById(Long.parseLong(id));
    }

    public FishingInstructor editFishingInstructor(FishingInstructor newFishingInstructor, String id) throws UserNotFoundException {

        return repository.findById(Long.parseLong(id)).map(fishingInstructor -> {
            fishingInstructor.setFirstName(newFishingInstructor.getFirstName());
            fishingInstructor.setLastName(newFishingInstructor.getLastName());
            fishingInstructor.setPhoneNumber(newFishingInstructor.getPhoneNumber());

            return repository.save(fishingInstructor);
        }).orElseThrow(()->  new UserNotFoundException(id));
    }

    public FishingInstructor editFishingInstructorPassword(String newPassword, String id) throws UserNotFoundException {

        return repository.findById(Long.parseLong(id)).map(fishingInstructor -> {
            fishingInstructor.setPassword(newPassword);
            return repository.save(fishingInstructor);
        }).orElseThrow(()->  new UserNotFoundException(id));
    }

    public FishingInstructor getFishingInstructorByEmail(String username) {
        return repository.findByEmail(username);
    }
    public FishingInstructor addFishingInstructor(FishingInstructor fishingInstructor){
        return repository.save(fishingInstructor);
    }

    public List<FishingInstructor> getUnregisteredFishingInstructors(){
        List<FishingInstructor> instructors=this.getFishingInstructors();
        List<FishingInstructor> filtered=new ArrayList<>();
        for (FishingInstructor instructor: instructors) {
            if(!instructor.isEnabled() && !instructor.getDeleted())
                filtered.add(instructor);
        }
        return filtered;
    }
}
