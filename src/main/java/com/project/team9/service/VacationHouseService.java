package com.project.team9.service;

import com.project.team9.model.resource.VacationHouse;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.repo.VacationHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacationHouseService {

    private final VacationHouseRepository repository;

    @Autowired
    public VacationHouseService(VacationHouseRepository vacationHouseRepository) {
        this.repository = vacationHouseRepository;
    }

    public List<VacationHouse> getVacationHouses() {
        return repository.findAll();
    }

    public VacationHouse getVacationHouse(Long id) {
        return repository.getById(id);
    }

    public void updateVacationHouses(VacationHouse house) {
        repository.save(house);
    }

    public void addVacationHouses(VacationHouse house) {
        repository.save(house);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public VacationHouse save(VacationHouse house) {
        return repository.save(house);
    }
}