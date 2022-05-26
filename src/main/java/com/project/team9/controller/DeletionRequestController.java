package com.project.team9.controller;

import com.project.team9.dto.DeleteReplayDTO;
import com.project.team9.model.request.DeleteRequest;
import com.project.team9.model.user.Client;
import com.project.team9.model.user.vendor.BoatOwner;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("deletionRequests")
public class DeletionRequestController {
    private final DeleteRequestService service;
    private final FishingInstructorService fishingInstructorService;
    private final ClientService clientService;
    private final BoatOwnerService boatOwnerService;
    private final VacationHouseOwnerService vacationHouseOwnerService;

    @Autowired
    public DeletionRequestController(DeleteRequestService service, FishingInstructorService fishingInstructorService, ClientService clientService, BoatOwnerService boatOwnerService, VacationHouseOwnerService vacationHouseOwnerService) {
        this.service = service;
        this.fishingInstructorService = fishingInstructorService;
        this.clientService = clientService;
        this.boatOwnerService = boatOwnerService;
        this.vacationHouseOwnerService = vacationHouseOwnerService;
    }

    @GetMapping
    private ResponseEntity<List<DeleteRequest>> getAllDeletionRequests() {
        return ResponseEntity.ok(service.getAllDeletionRequests());
    }

    @PostMapping(path="/validateDeletion",produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> deleteUser(@RequestBody DeleteReplayDTO deleteReplayDTO) {
        String response="";
        if(deleteReplayDTO.getType().equals("approve")) {
            if (fishingInstructorService.getFishingInstructorByEmail(deleteReplayDTO.getUsername()) != null) {
                FishingInstructor fishingInstructor = fishingInstructorService.getFishingInstructorByEmail(deleteReplayDTO.getUsername());
                fishingInstructor.setDeleted(true);
                fishingInstructorService.addFishingInstructor(fishingInstructor);
            } else if (boatOwnerService.getBoatOwnerByEmail(deleteReplayDTO.getUsername()) != null) {
                BoatOwner boatOwner = boatOwnerService.getBoatOwnerByEmail(deleteReplayDTO.getUsername());
                boatOwner.setDeleted(true);
                boatOwnerService.save(boatOwner);
            } else if (vacationHouseOwnerService.getVacationHouseOwnerByEmail(deleteReplayDTO.getUsername()) != null) {
                VacationHouseOwner vacationHouseOwner = vacationHouseOwnerService.getVacationHouseOwnerByEmail(deleteReplayDTO.getUsername());
                vacationHouseOwner.setDeleted(true);
                vacationHouseOwnerService.save(vacationHouseOwner);
            } else if (clientService.getClientByEmail(deleteReplayDTO.getUsername()) != null) {
                Client client = clientService.getClientByEmail(deleteReplayDTO.getUsername());
                client.setDeleted(true);
                clientService.addClient(client);
            }
            response = "Korisnik je obrisan";
        }else {
            response = "Korisnik nije obrisan";
        }
        DeleteRequest deleteRequest=service.getById(deleteReplayDTO.getRequestId());
        deleteReplayDTO.setComment(deleteReplayDTO.getComment());
        deleteRequest.setDeleted(true);
        service.addDeleteRequest(deleteRequest);
        return ResponseEntity.ok(response);
    }
}
