package com.project.team9.service;

import com.project.team9.model.request.RegistrationRequest;
import com.project.team9.model.user.*;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.repo.RegistrationRequestRepository;
import org.springframework.stereotype.Service;

@Service

public class RegistrationService {

    private final UserServiceSecurity userServiceSecurity;
    private final RegistrationRequestRepository registrationRequestRepository;


    public RegistrationService(UserServiceSecurity userServiceSecurity, RegistrationRequestRepository registrationRequestRepository) {
        this.userServiceSecurity = userServiceSecurity;
        this.registrationRequestRepository = registrationRequestRepository;
    }

    public String register(RegistrationRequest registrationRequest) {
        Client user = null;
        switch (registrationRequest.getUserRole()) {
            case "CLIENT":
                user = new Client(
                        registrationRequest.getPassword(),
                        registrationRequest.getFirstName(),
                        registrationRequest.getLastName(),
                        registrationRequest.getEmail(),
                        registrationRequest.getPhoneNumber(),
                        registrationRequest.getPlace(),
                        registrationRequest.getNumber(),
                        registrationRequest.getStreet(),
                        registrationRequest.getCountry(),
                        UserRole.CLIENT,
                        Boolean.FALSE);
                break;
            case "FISHING_INSTRUCTOR":
            case "VACATION_HOUSE_OWNER":
            case "BOAT_OWNER":
                addRegistrationRequest(registrationRequest);
                break;
            default:
                break;
        }
        return userServiceSecurity.signUpUser(user,registrationRequest.getEmail());
    }

    public RegistrationRequest addRegistrationRequest(RegistrationRequest registrationRequest) {
        return registrationRequestRepository.save(registrationRequest);
    }
}
