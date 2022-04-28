package com.project.team9.controller;

import com.project.team9.model.request.RegistrationRequest;
import com.project.team9.service.RegistrationService;
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
    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }

}
