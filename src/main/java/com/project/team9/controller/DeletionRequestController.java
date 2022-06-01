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

    @Autowired
    public DeletionRequestController(DeleteRequestService service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<List<DeleteRequest>> getAllDeletionRequests() {
        return ResponseEntity.ok(service.getAllDeletionRequests());
    }

    @PostMapping(path="/validateDeletion",produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> deleteUser(@RequestBody DeleteReplayDTO deleteReplayDTO) {
        return ResponseEntity.ok(service.processDeletionRequest(deleteReplayDTO));

    }
}
