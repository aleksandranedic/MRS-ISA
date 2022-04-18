package com.project.team9.service;

import com.project.team9.model.resource.VacationHouse;
import com.project.team9.repo.VacationHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void addVacationHouses(VacationHouse house) {
        repository.save(house);
    }
}
