package com.project.team9.service;

import com.project.team9.model.user.vendor.BoatOwner;
import com.project.team9.repo.BoatOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoatOwnerService {

    private final BoatOwnerRepository repository;


    @Autowired
    public BoatOwnerService(BoatOwnerRepository repository) {
        this.repository = repository;
    }

    public BoatOwner getOwner(Long id) {
        return repository.getById(id);
    }

    public void addOwner(BoatOwner owner) {
        repository.save(owner);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public BoatOwner save(BoatOwner owner) {
        return repository.save(owner);
    }

}
