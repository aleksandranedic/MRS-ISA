package com.project.team9.controller;

import com.project.team9.dto.AdventureDTO;
import com.project.team9.dto.ReservationDTO;
import com.project.team9.service.AdventureReservationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="reservation/adventure")
public class AdventureReservationController {

    private final AdventureReservationService service;

    public AdventureReservationController(AdventureReservationService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public Long add(@RequestBody ReservationDTO dto){
        return service.createReservation(dto);
    }

}
