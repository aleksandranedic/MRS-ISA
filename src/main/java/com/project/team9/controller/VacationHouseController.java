package com.project.team9.controller;

import com.project.team9.dto.*;
import com.project.team9.exceptions.ReservationNotAvailableException;
import com.project.team9.dto.*;
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
import java.util.List;

@RestController
@RequestMapping(path = "house")
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


    @GetMapping("/reservation/vacationHouseOwner/{id}")
    public List<ReservationDTO> getReservationsForOwner(@PathVariable Long id) {
        return service.getReservationsForOwner(id);
    }

    @GetMapping("/reservation/vacationHouse/{id}")
    public List<ReservationDTO> getReservationsForVacationHouse(@PathVariable Long id) {
        return service.getReservationsForVacationHouse(id);
    }

    @GetMapping("/reservation/client/{id}")
    public List<ReservationDTO> getReservationsForClient(@PathVariable Long id) {
        return service.getReservationsForClient(id);
    }

    @PostMapping("/reservation/add")
    public Long addReservation(@RequestBody NewReservationDTO dto) throws ReservationNotAvailableException {
        return service.createReservation(dto);
    }

    @PostMapping("/reservation/busyPeriod/add")
    public Long addBusyPeriod(@RequestBody NewBusyPeriodDTO dto) throws ReservationNotAvailableException {
        return service.createBusyPeriod(dto);
    }

    @GetMapping("/reservation/busyPeriod/vacationHouse/{id}")
    public List<ReservationDTO> getBusyPeriodForVacationHouse(@PathVariable Long id) {
        return  service.getBusyPeriodForVacationHouse(id);
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VacationHouse getVacationHouse(@PathVariable String id) {
        Long houseId = Long.parseLong(id);
        return service.getVacationHouse(houseId);
    }

    @GetMapping(value = "getRating/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public double getRating(@PathVariable String id) {
        return service.getRatingForHouse(Long.parseLong(id));
    }


    @GetMapping(value = "getReservations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationDTO> getReservations(@PathVariable String id) {
        return service.getReservations(Long.parseLong(id));
    }

    @PostMapping(value = "createHouse")
    public Long addVacationHouseForOwner(VacationHouseDTO house, @RequestParam("fileImage") MultipartFile[] multipartFiles) throws IOException {
        return service.createHouse(house, multipartFiles);
    }

    @PostMapping(value = "updateHouse/{id}")
    public VacationHouseDTO updateVacationHouse(@PathVariable String id, VacationHouseDTO house, @RequestParam("fileImage") MultipartFile[] multipartFiles) throws IOException {
        return service.updateHouse(id, house, multipartFiles);
    }

    @PostMapping(value = "addQuickReservation/{id}")
    public Boolean addQuickReservation(@PathVariable String id, VacationHouseQuickReservationDTO quickReservation) {
        return service.addQuickReservation(Long.parseLong(id), quickReservation);
    }

    @PostMapping(value = "updateQuickReservation/{id}")
    public Boolean updateQuickReservation(@PathVariable String id, VacationHouseQuickReservationDTO quickReservation) {
        return service.updateQuickReservation(Long.parseLong(id), quickReservation);
    }

    @PostMapping(value = "deleteQuickReservation/{id}")
    public Boolean deleteQuickReservation(@PathVariable String id, VacationHouseQuickReservationDTO quickReservation) {
        return service.deleteQuickReservation(Long.parseLong(id), quickReservation);
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

    @GetMapping("/clientCanReview/{resourceId}/{clientId}")
    public Boolean clientCanReview(@PathVariable Long resourceId, @PathVariable Long clientId){
        return service.clientCanReview(resourceId, clientId);
    }
}