package com.project.team9.service;

import com.project.team9.model.request.RegistrationRequest;
import com.project.team9.model.user.*;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.repo.RegistrationRequestRepository;
import org.springframework.stereotype.Service;

@Service

public class RegistrationService {

    private final UserServiceSecurity userServiceSecurity;

    private  final RegistrationRequestRepository registrationRequestRepository;


    public RegistrationService(UserServiceSecurity userServiceSecurity, RegistrationRequestRepository registrationRequestRepository) {
        this.userServiceSecurity = userServiceSecurity;
        this.registrationRequestRepository = registrationRequestRepository;
    }
    public String register(RegistrationRequest registrationRequest) {
//        return registrationRequestRepository.save(registrationRequest).toString();
        User user=null;
        switch (registrationRequest.getUserRole().name()){
            case "CLIENT":
                user=new Client(
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
                user=new FishingInstructor();
                break;
            case "VACATION_HOUSE_OWNER":
                break;
            case "BOAT_OWNER":
                break;
        }
//        return userServiceSecurity.signUpUser(
//                new User(
//                        String password, UserRole userRole, Boolean locked, Boolean enabled, Boolean deleted, Long userId
//                        registrationRequest.getEmail(),
//                        registrationRequest.getUserRole(),
//                        registrationRequest.getLocked(),
//                        registrationRequest.getUserRole(),
//                        registrationRequest.getUserRole(),
//                        registrationRequest.getUserRole(),
//                        registrationRequest.getUserRole(),

//                        registrationRequest.get
//                )
//        );
        return userServiceSecurity.signUpUser(user);
    }
}
//    private String email;
//    private String password;
//    @Enumerated(EnumType.STRING)
//    private UserRole userRole;
//    private Boolean locked;
//    private Boolean enabled;
//    private Boolean deleted;
//    private Long userId;
