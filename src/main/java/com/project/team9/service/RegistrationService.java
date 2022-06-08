package com.project.team9.service;

import com.project.team9.model.request.RegistrationRequest;
import com.project.team9.model.user.Client;
import com.project.team9.model.user.Role;
import com.project.team9.model.user.User;
import com.project.team9.security.email.EmailSender;
import com.project.team9.security.token.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private final UserServiceSecurity userServiceSecurity;
    private final RegistrationRequestService registrationRequestService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final RoleService roleService;


    @Autowired
    public RegistrationService(UserServiceSecurity userServiceSecurity, RegistrationRequestService registrationRequestService, EmailService emailService, ConfirmationTokenService confirmationTokenService, RoleService roleService) {
        this.userServiceSecurity = userServiceSecurity;
        this.registrationRequestService = registrationRequestService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
        this.roleService = roleService;
    }

    public String register(RegistrationRequest registrationRequest) {

        Role role = roleService.findRoleByName(registrationRequest.getUserRole());
        String response = "";
        if (role == null) {
            role = new Role(registrationRequest.getUserRole());
            roleService.save(role);
        }

        switch (registrationRequest.getUserRole()) {
            case "CLIENT":
                Client user = new Client(
                        registrationRequest.getPassword(),
                        registrationRequest.getFirstName(),
                        registrationRequest.getLastName(),
                        registrationRequest.getEmail(),
                        registrationRequest.getPhoneNumber(),
                        registrationRequest.getPlace(),
                        registrationRequest.getNumber(),
                        registrationRequest.getStreet(),
                        registrationRequest.getCountry(),
                        Boolean.FALSE, role);
                String token = userServiceSecurity.signUpUser(user);
                String link = "<a href=\""+"http://localhost:3000/confirmedEmail/" + token+"\">Aktivirajte</a>";
                String email=emailService.buildHTMLEmail(user.getName(),"Hvala na registraciji. Molim Vas kliknite link ispod da bi aktivirali svoj nalog:", link ,"Verifikacija emaila");
                emailService.send(user.getEmail(), email, "Verifikacija emaila");
                ConfirmationToken confirmationToken = new ConfirmationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        user
                );
                confirmationTokenService.saveConfirmationToken(confirmationToken);
                response = "Uspesno ste izvrsili registraciju.\nProverite email kako biste verifikovali svoj nalog";
                break;
            case "FISHING_INSTRUCTOR":
            case "BOAT_OWNER":
            case "VACATION_HOUSE_OWNER":
                registrationRequestService.addRegistrationRequest(registrationRequest);
                response = "Uspesno ste poslali zahtev o registraciji";
                break;
        }
        return response;
    }


    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElse(null);
        if (confirmationToken == null)
            return "Token ne postoji";
        if (confirmationToken.getConfirmedAt() != null) {
            return "Vaš email je vec verifikovan";
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            return "Vaš verifikacioni token je istekao";
        }
        confirmationTokenService.setConfirmedAt(token);
        User user = (User) userServiceSecurity.loadUserByUsername(confirmationToken.getUser().getEmail());
        user.setEnabled(true);
        userServiceSecurity.addClient((Client) user);

        return "Vaša verifikacija je uspesna";
    }
}
