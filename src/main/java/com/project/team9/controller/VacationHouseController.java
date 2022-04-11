package com.project.team9.controller;

import com.project.team9.model.resource.VacationHouse;
import com.project.team9.service.VacationHouseService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public void addHouse(@RequestBody VacationHouse house) {
        service.addVacationHouses(house);
    }

}
