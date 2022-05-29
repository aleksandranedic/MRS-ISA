package com.project.team9.controller;

import com.project.team9.dto.*;
import com.project.team9.exceptions.ReservationNotAvailableException;
import com.project.team9.model.resource.Boat;
import com.project.team9.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path="boat")
@CrossOrigin("*")
public class BoatController {

    private final BoatService service;

    @Autowired
    public BoatController(BoatService service) {
        this.service = service;
    }

    @GetMapping
    public List<Boat> getBoats() {
        return service.getBoats();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boat getBoat(@PathVariable String id) {
        Long houseId = Long.parseLong(id);
        return service.getBoat(houseId);
    }
    @PostMapping(value = "createBoat")
    public Long addBoatForOwner(BoatDTO boat, @RequestParam("fileImage") MultipartFile[] multipartFiles) throws IOException {
        return service.createBoat(boat, multipartFiles);
    }

    @PostMapping(value = "updateBoat/{id}")
    public BoatDTO updateVacationHouse(@PathVariable String id, BoatDTO boatDTO, @RequestParam("fileImage") MultipartFile[] multipartFiles) throws IOException {
        return service.updateBoat(id, boatDTO, multipartFiles);
    }

    @PostMapping(value = "addQuickReservation/{id}")
    public Boolean addQuickReservation(@PathVariable String id, BoatQuickReservationDTO quickReservation) {
        return service.addQuickReservation(Long.parseLong(id), quickReservation);
    }

    @PostMapping(value = "updateQuickReservation/{id}")
    public Boolean updateQuickReservation(@PathVariable String id, BoatQuickReservationDTO quickReservation) {
        return service.updateQuickReservation(Long.parseLong(id), quickReservation);
    }

    @PostMapping(value = "deleteQuickReservation/{id}")
    public Boolean deleteQuickReservation(@PathVariable String id, BoatQuickReservationDTO quickReservation) {
        return service.deleteQuickReservation(Long.parseLong(id), quickReservation);
    }

    @GetMapping(value = "boatprof/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BoatDTO getBoatDTO(@PathVariable String id) {
        Long boatId = Long.parseLong(id);
        return service.getBoatDTO(boatId);
    }

    @GetMapping(value = "getOwner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResourceOwnerDTO getOwner(@PathVariable String id) {
        return service.getOwner(Long.parseLong(id));
    }

    @GetMapping(value = "getReservations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationDTO> getReservations(@PathVariable String id) {
        return service.getReservations(Long.parseLong(id));
    }

    @GetMapping(value = "getownerboats/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BoatCardDTO> getOwnerHouses(@PathVariable String id) {
        Long owner_id = Long.parseLong(id);
        return service.getOwnerBoats(owner_id);
    }

    @GetMapping(value = "getownerboat/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BoatCardDTO getOwnerHouse(@PathVariable String id) {
        return service.getBoatCard(Long.parseLong(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteVacationHouse(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reservation/boatOwner/{id}")
    public List<ReservationDTO> getReservationsForOwner(@PathVariable Long id) {
        return  service.getReservationsForOwner(id);
    }

    @GetMapping("/reservation/boat/{id}")
    public List<ReservationDTO> getReservationsForBoat(@PathVariable Long id) {
        return  service.getReservationsForBoat(id);
    }

    @GetMapping("/reservation/client/{id}")
    public List<ReservationDTO> getReservationsForClient(@PathVariable Long id) {
        return  service.getReservationsForClient(id);
    }

    @PostMapping("/reservation/add")
    public Long addReservation(@RequestBody NewReservationDTO dto) throws ReservationNotAvailableException {
        return service.createReservation(dto);
    }

    @PostMapping("/reservation/busyPeriod/add")
    public Long addBusyPeriod(@RequestBody NewBusyPeriodDTO dto) throws ReservationNotAvailableException {
        return service.createBusyPeriod(dto);
    }

    @GetMapping("/reservation/busyPeriod/boat/{id}")
    public List<ReservationDTO> getBusyPeriodForVacationHouse(@PathVariable Long id) {
        return  service.getBusyPeriodForBoat(id);
    }

    @GetMapping("/clientCanReview/{resourceId}/{clientId}")
    public Boolean clientCanReview(@PathVariable Long resourceId, @PathVariable Long clientId){
        return service.clientCanReview(resourceId, clientId);
    }
}