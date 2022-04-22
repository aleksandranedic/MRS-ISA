package com.project.team9.service;

import com.project.team9.dto.HouseCardDTO;
import com.project.team9.model.resource.VacationHouse;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.repo.VacationHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<HouseCardDTO> getOwnerHouses(Long owner_id) {
        List<VacationHouse> houses = repository.findByOwnerId(owner_id);
        List<HouseCardDTO> houseCards = new ArrayList<HouseCardDTO>();
        for(VacationHouse house : houses){
            String address = house.getAddress().getStreet() + " " + house.getAddress().getNumber() + ", " + house.getAddress().getPlace() + ", " + house.getAddress().getCountry();
            String thumbnail = "./images/housenotext.png";
            if (house.getImages().size() > 0){
                thumbnail = house.getImages().get(0).getPath();
            }
            houseCards.add(new HouseCardDTO(house.getId(), thumbnail, house.getTitle(), house.getDescription(), address));
        }
        return houseCards;
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