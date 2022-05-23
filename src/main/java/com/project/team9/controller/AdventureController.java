package com.project.team9.controller;

import com.project.team9.dto.AdventureDTO;
import com.project.team9.dto.AdventureQuickReservationDTO;
import com.project.team9.dto.ReservationDTO;
import com.project.team9.dto.NewReservationDTO;
import com.project.team9.exceptions.ReservationNotAvailableException;
import com.project.team9.model.reservation.AdventureReservation;
import com.project.team9.model.resource.Adventure;
import com.project.team9.service.AdventureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path="adventure")
public class AdventureController {

    private final AdventureService service;

    @Autowired
    public AdventureController(AdventureService adventureService) {
        this.service = adventureService;
    }

    @PostMapping("/reservation/add")
    public Long addReservation(@RequestBody NewReservationDTO dto){
        try {
            return service.createReservation(dto);
        } catch (ReservationNotAvailableException e) {
            return -1L;
        }
    }

    @GetMapping("/reservation/fishingInstructor/{id}")
    public List<AdventureReservation> getReservationsForInstructor(@PathVariable Long id) {
        return  service.getReservationsForFishingInstructor(id);
    }

    @GetMapping("/reservation/adventure/{id}")
    public List<ReservationDTO> getReservationsForAdventure(@PathVariable Long id) {
        return  service.getReservationsForAdventure(id);
    }

    @GetMapping("/reservation/client/{id}")
    public List<AdventureReservation> getReservationsForClient(@PathVariable Long id) {
        return  service.getReservationsForClient(id);
    }

    @GetMapping
    public List<Adventure> getAdventures() {
        return service.getAdventures();
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Adventure getById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping(value="quickReservations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AdventureQuickReservationDTO> getQuickReservations(@PathVariable String id) {
        return service.getQuickReservations(id);
    }
    @PostMapping(value = "addQuickReservation/{id}")
    public Boolean addQuickReservation(@PathVariable String id, AdventureQuickReservationDTO quickReservation) {
        return service.addQuickReservation(id, quickReservation);
    }

    @PostMapping(value = "updateQuickReservation/{id}")
    public Boolean updateQuickReservation(@PathVariable String id, AdventureQuickReservationDTO quickReservation) {
        return service.updateQuickReservation(id, quickReservation);
    }

    @GetMapping(value="/dto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AdventureDTO getDTOById(@PathVariable String id) {
        return service.getDTOById(id);
    }

    @PostMapping("/{id}/edit")
    public Adventure edit(@RequestBody AdventureDTO adventure, @PathVariable String id, @RequestParam("fileImage") MultipartFile[] multipartFiles) throws IOException {
        System.out.println(id);
        return service.editAdventure(id, adventure, multipartFiles);

    }

    @PostMapping("/add")
    public Long addReservation(AdventureDTO adventure, @RequestParam("fileImage") MultipartFile[] multipartFiles) throws IOException {
        return service.createAdventure(adventure, multipartFiles);
    }

    @GetMapping("/owner/{ownerId}")
    List<Adventure> findAdventuresWithOwner(@PathVariable String ownerId) {
        return service.findAdventuresWithOwner(ownerId);
    }


    @DeleteMapping("/{id}/delete")
    void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }
}
