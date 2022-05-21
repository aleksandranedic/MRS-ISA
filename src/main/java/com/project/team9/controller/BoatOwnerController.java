package com.project.team9.controller;

import com.project.team9.dto.UpdateOwnerDTO;
import com.project.team9.model.resource.Boat;
import com.project.team9.model.user.vendor.BoatOwner;
import com.project.team9.service.BoatOwnerService;
import com.project.team9.service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "boatowner")
public class BoatOwnerController {

    private final BoatOwnerService service;
    private final BoatService boatService;

    @Autowired
    public BoatOwnerController(BoatOwnerService service, BoatService vacationHouseService) {
        this.service = service;
        this.boatService = vacationHouseService;
    }

    @GetMapping
    public List<BoatOwner> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BoatOwner getOwner(@PathVariable String id) {
        return service.getOwner(Long.parseLong(id));
    }

    @PostMapping(value = "updateOwner/{id}")
    public void updateOwner(@PathVariable String id, UpdateOwnerDTO owner) {
        service.updateOwner(Long.parseLong(id), owner);
    }
    @PostMapping(value = "checkPassword/{id}", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean checkPassword(@PathVariable String id, @RequestBody  String oldPassword) {
        return service.checkPassword(Long.parseLong(id), oldPassword);
    }

    @PostMapping(value = "updatePassword/{id}",consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updatePassword(@PathVariable String id, @RequestBody String newPassword) {
        service.updatePassword(Long.parseLong(id), newPassword);
    }
    @PostMapping("addBoat/{id}")
    public void addBoatForOwner(@PathVariable String id, @RequestBody String boatId){
        boatId = boatId.substring(0, boatId.length() - 1);
        BoatOwner bo = service.getOwner(Long.parseLong(id));
        Boat bt = boatService.getBoat(Long.parseLong(boatId));
        bt.setOwner(bo);
        boatService.addBoat(bt);
        bo.addBoat(bt);
        service.save(bo);
    }

    @PostMapping("/{id}/add")
    public void addOwner(@RequestBody BoatOwner owner) {
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

