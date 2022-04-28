package com.project.team9.controller;

import com.project.team9.dto.HouseCardDTO;
import com.project.team9.dto.VacationHouseDTO;
import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.Tag;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.resource.VacationHouse;
import com.project.team9.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @PostMapping(value = "createHouse")
    public Long addVacationHouseForOwner(VacationHouseDTO house, @RequestParam("fileImage") MultipartFile[] multipartFiles) throws IOException {
        return service.createHouse(house, multipartFiles);
    }

    @PostMapping(value = "updateHouse/{id}")
    public VacationHouseDTO updateVacationHouse(@PathVariable String id, VacationHouseDTO house, @RequestParam("fileImage") MultipartFile[] multipartFiles) throws IOException {
        return service.updateHouse(id, house, multipartFiles);
    }

    @GetMapping(value = "houseprof/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VacationHouseDTO getVacationHouseDTO(@PathVariable String id) {
        Long houseId = Long.parseLong(id);
        return service.getVacationHouseDTO(houseId);
    }

    @GetMapping(value = "getownerhouses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HouseCardDTO> getOwnerHouses(@PathVariable String id) {
        Long owner_id = Long.parseLong(id);
        return service.getOwnerHouses(owner_id);
    }

    @GetMapping(value = "getownerhouse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HouseCardDTO getOwnerHouse(@PathVariable String id) {
        return service.getVacationHouseCard(Long.parseLong(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteVacationHouse(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}