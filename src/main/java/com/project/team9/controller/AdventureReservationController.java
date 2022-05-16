package com.project.team9.controller;

import com.project.team9.dto.AdventureDTO;
import com.project.team9.dto.ReservationDTO;
import com.project.team9.model.reservation.AdventureReservation;
import com.project.team9.service.AdventureReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="adventure/reservation")
public class AdventureReservationController {

    private final AdventureReservationService service;

    public AdventureReservationController(AdventureReservationService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public Long add(@RequestBody ReservationDTO dto){
        return service.createReservation(dto);
    }

    @GetMapping("/fishingInstructor/{id}")
    public List<AdventureReservation> getReservationsForInstructor(@PathVariable Long id) {
        return  service.getReservationsForFishingInstructor(id);
    }

    @GetMapping("/adventure/{id}")
    public List<AdventureReservation> getReservationsForAdventure(@PathVariable Long id) {
        return  service.getReservationsForAdventure(id);
    }

}
