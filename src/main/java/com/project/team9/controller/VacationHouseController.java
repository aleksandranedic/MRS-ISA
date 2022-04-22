package com.project.team9.controller;

import com.project.team9.dto.HouseCardDTO;
import com.project.team9.dto.VacationHouseDTO;
import com.project.team9.model.resource.VacationHouse;
import com.project.team9.service.VacationHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="house")
@CrossOrigin("*")
public class VacationHouseController {

    private final VacationHouseService service;

    @Autowired
    public VacationHouseController(VacationHouseService vacationHouseService) {
        this.service = vacationHouseService;
    }

    @GetMapping
    public List<VacationHouse> getHouses() {
        return service.getVacationHouses();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VacationHouse getVacationHouse(@PathVariable String id) {
        Long houseId = Long.parseLong(id);
        return service.getVacationHouse(houseId);
    }

    @GetMapping(value = "houseprof/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VacationHouseDTO getVacationHouseDTO(@PathVariable String id) {
        Long houseId = Long.parseLong(id);
        return service.getVacationHouseDTO(houseId);
    }

    @PostMapping("/update")
    public void updateHouse(@RequestBody VacationHouse house) {
        service.updateVacationHouses(house);
    }

    @PostMapping("/add")
    public void addHouse(@RequestBody VacationHouse house) {
        service.addVacationHouses(house);
    }

    @GetMapping(value = "getownerhouses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HouseCardDTO> getOwnerHouses(@PathVariable String id) {
        Long owner_id = Long.parseLong(id);
        return service.getOwnerHouses(owner_id);
    }

    @PutMapping("/{id}")
    public VacationHouse updateVacationHouse(@PathVariable String id, @RequestBody VacationHouse house) {
        Long houseId = Long.parseLong(id);
        VacationHouse vacationHouse = service.getVacationHouse(houseId);
        vacationHouse.setTitle(house.getTitle());
        vacationHouse.setPricelist(house.getPricelist());
        vacationHouse.setDescription(house.getDescription());
        vacationHouse.setNumberOfRooms(house.getNumberOfRooms());
        vacationHouse.setNumberOfBedsPerRoom(house.getNumberOfBedsPerRoom());
        vacationHouse.setRulesAndRegulations(house.getRulesAndRegulations());
        vacationHouse.setAddress(house.getAddress());
        vacationHouse.setAdditionalServices(house.getAdditionalServices());
        vacationHouse.setImages(house.getImages());
        vacationHouse = service.save(house);
        return vacationHouse;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteVacationHouse(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}