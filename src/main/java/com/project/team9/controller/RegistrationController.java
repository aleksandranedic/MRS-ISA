package com.project.team9.controller;

import com.project.team9.model.request.RegistrationRequest;
import com.project.team9.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "registration")
@CrossOrigin("*")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }
    @PostMapping
    public String register(@RequestBody RegistrationRequest registrationRequest){
        return registrationService.register(registrationRequest);
    }
    @GetMapping(path = "/confirm/{token}")
    public ResponseEntity<String> confirm(@PathVariable("token") String token){
        //ovde treba da ga vratim na login stranicu ne znam kako to jos
        String response=registrationService.confirmToken(token);
        if(response.equals("Vasa verifikacija je uspesna"))
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.badRequest().body(response);
    }

}
