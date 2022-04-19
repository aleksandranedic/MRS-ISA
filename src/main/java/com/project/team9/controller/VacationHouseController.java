package com.project.team9.controller;

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

    @GetMapping("get/house/{id}")
    public VacationHouse getVacationHouse(@PathVariable("id") String idStr) {
        Long id = Long.parseLong(idStr);
        return service.getVacationHouse(id);
    }

    @PostMapping("/update/house")
    public void updateHouse(@RequestBody VacationHouse house) {
        service.updateVacationHouses(house);
    }

    @PostMapping("/add/house")
    public void addHouse(@RequestBody VacationHouse house) {
        service.addVacationHouses(house);
    }

    @PutMapping("/{id}")
    public VacationHouse updateVacationHouse(@PathVariable String idStr, @RequestBody VacationHouse house) {
        Long id = Long.parseLong(idStr);
        VacationHouse vacationHouse = service.getVacationHouse(id);
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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VacationHouse getById(@PathVariable String idStr) {
        Long id = Long.parseLong(idStr);
        return service.getVacationHouse(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteVacationHouse(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}