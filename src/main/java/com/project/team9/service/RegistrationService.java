package com.project.team9.service;

import com.project.team9.model.request.RegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    public String register(RegistrationRequest registrationRequest) {
        return "works";
    }
}
