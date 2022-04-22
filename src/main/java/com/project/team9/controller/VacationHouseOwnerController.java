package com.project.team9.controller;

import com.project.team9.model.resource.VacationHouse;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.service.VacationHouseOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "houseowner")
public class VacationHouseOwnerController {

    private final VacationHouseOwnerService service;

    @Autowired
    public VacationHouseOwnerController(VacationHouseOwnerService vacationHouseOwnerService) {
        this.service = vacationHouseOwnerService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VacationHouseOwner getOwner(@PathVariable String id) {
        Long ownerId = Long.parseLong(id);
        return service.getOwner(ownerId);
    }

    @PostMapping("/add")
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

