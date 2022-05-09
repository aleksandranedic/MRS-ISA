package com.project.team9.service;

import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.repo.VacationHouseOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacationHouseOwnerService {

    private final VacationHouseOwnerRepository repository;

    @Autowired
    public VacationHouseOwnerService(VacationHouseOwnerRepository vacationHouseOwnerRepository) {
        this.repository = vacationHouseOwnerRepository;
    }

    public VacationHouseOwner getOwner(Long id) {
        return repository.getById(id);
    }

    public void addOwner(VacationHouseOwner owner) {
        repository.save(owner);
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
}