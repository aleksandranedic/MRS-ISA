package com.project.team9.service;

import com.project.team9.model.user.vendor.BoatOwner;
import com.project.team9.repo.BoatOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoatOwnerService {
    private final BoatOwnerRepository boatOwnerRepository;

    @Autowired
    public BoatOwnerService(BoatOwnerRepository boatOwnerRepository) {
        this.boatOwnerRepository = boatOwnerRepository;
    }

    public BoatOwner addClient(BoatOwner boatOwner) {
        return boatOwnerRepository.save(boatOwner);
    }

    public BoatOwner getBoatOwnerByEmail(String username) {
        return boatOwnerRepository.findByEmail(username);
    }
}
