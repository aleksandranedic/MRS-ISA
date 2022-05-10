package com.project.team9.controller;

import com.project.team9.model.request.RegistrationRequest;
import com.project.team9.service.RegistrationService;
import org.springframework.http.HttpStatus;
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
        String response=registrationService.confirmToken(token);
        if(response.equals("Vasa verifikacija je uspesna"))
            return new ResponseEntity<String>(response, HttpStatus.OK);
        else
            return new ResponseEntity<String>(response,HttpStatus.OK);
    }

}
