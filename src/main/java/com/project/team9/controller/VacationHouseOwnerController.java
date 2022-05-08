package com.project.team9.controller;

import com.project.team9.model.resource.VacationHouse;
import com.project.team9.model.user.vendor.BoatOwner;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.service.VacationHouseOwnerService;
import com.project.team9.service.VacationHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "houseowner")
public class VacationHouseOwnerController {

    private final VacationHouseOwnerService service;
    private final VacationHouseService vacationHouseService;

    @Autowired
    public VacationHouseOwnerController(VacationHouseOwnerService vacationHouseOwnerService, VacationHouseService vacationHouseService) {
        this.service = vacationHouseOwnerService;
        this.vacationHouseService = vacationHouseService;
    }

    @GetMapping
    public List<VacationHouseOwner> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VacationHouseOwner getOwner(@PathVariable String id) {
        Long ownerId = Long.parseLong(id);
        return service.getOwner(ownerId);
    }

    @PostMapping("addHouse/{id}")
    public void addVacationHouseForOwner(@PathVariable String id, @RequestBody String houseId){
        houseId = houseId.substring(0, houseId.length() - 1);
        VacationHouseOwner vho = service.getOwner(Long.parseLong(id));
        VacationHouse vh = vacationHouseService.getVacationHouse(Long.parseLong(houseId));
        vh.setOwner(vho);
        vacationHouseService.save(vh);
        vho.addVacationHouse(vh);
        service.save(vho);
    }

    @PostMapping("/{id}/add")
    public void addOwner(@RequestBody VacationHouseOwner owner) {
        service.addOwner(owner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOwner(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //    @PutMapping("/{id}")
    //    public VacationHouseOwner updateOwner(@PathVariable String idStr, @RequestBody VacationHouseOwner owner) {
    //        Long id = Long.parseLong(idStr);
    //        VacationHouseOwner vacationHouseOwner = service.getOwner(id);
    //        vacationHouseOwner.setFirstName(owner.getFirstName());
    //        vacationHouseOwner.setLastName(owner.getLastName());
    //        vacationHouseOwner.setPhoneNumber(owner.getPhoneNumber());
    //        vacationHouseOwner.setPassword(owner.getPassword());
    //        vacationHouseOwner = service.save(owner);
    //        return vacationHouseOwner;
    //    }
}

