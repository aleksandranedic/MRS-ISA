package com.project.team9.controller;

import com.project.team9.dto.VendorRegistrationRequestReplay;
import com.project.team9.model.request.RegistrationRequest;
import com.project.team9.service.VendorRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "vendorRegistration")
@CrossOrigin("*")
public class UnregisteredVendorsController {

    private final VendorRegistrationService service;

    public UnregisteredVendorsController(VendorRegistrationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RegistrationRequest>> getAllRegistrationRequests() {
        return ResponseEntity.ok(service.getAllVendorRegistrations());
    }

    @PostMapping(value = "/validate")
    public ResponseEntity<String> approveRegistration(@RequestBody VendorRegistrationRequestReplay replay) {
        if (replay.getType().equals("approve")) {
            return ResponseEntity.ok(service.validateVendor(replay));
        } else {
            return ResponseEntity.ok(service.deleteRegistrationRequest(replay));
        }

    }
}
