package com.project.team9.service;

import com.project.team9.model.request.RegistrationRequest;
import com.project.team9.model.resource.Adventure;
import com.project.team9.model.resource.Boat;
import com.project.team9.model.user.Client;
import com.project.team9.model.user.Role;
import com.project.team9.model.user.User;
import com.project.team9.model.user.vendor.BoatOwner;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.repo.RegistrationRequestRepository;
import com.project.team9.security.email.EmailSender;
import com.project.team9.security.token.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class RegistrationService {

    private final UserServiceSecurity userServiceSecurity;
    private final RegistrationRequestRepository registrationRequestRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final RoleService roleService;
    private final FishingInstructorService fishingInstructorService;
    private final BoatOwnerService boatOwnerService;
    private final VacationHouseOwnerService vacationHouseOwnerService;

    @Autowired
    public RegistrationService(UserServiceSecurity userServiceSecurity, RegistrationRequestRepository registrationRequestRepository, ConfirmationTokenService confirmationTokenService, EmailSender emailSender, RoleService roleService, FishingInstructorService fishingInstructorService, BoatOwnerService boatOwnerService, VacationHouseOwnerService vacationHouseOwnerService) {
        this.userServiceSecurity = userServiceSecurity;
        this.registrationRequestRepository = registrationRequestRepository;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
        this.roleService = roleService;
        this.fishingInstructorService = fishingInstructorService;
        this.boatOwnerService = boatOwnerService;
        this.vacationHouseOwnerService = vacationHouseOwnerService;
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
                String link = "http://localhost:3000/confirmedEmail/" + token;
                emailSender.send(
                        registrationRequest.getEmail(),
                        buildEmail(registrationRequest.getFirstName() + " " + registrationRequest.getLastName(), link));
                //moras da napravis token bre
                ConfirmationToken confirmationToken = new ConfirmationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        user
                );
                response = "Uspesno ste izvrsili registraciju.\nProverite email kako biste verifikovali svoj nalog";
                break;
            case "FISHING_INSTRUCTOR":
                FishingInstructor fishingInstructor = new FishingInstructor(
                        registrationRequest.getPassword(),
                        registrationRequest.getFirstName(),
                        registrationRequest.getLastName(),
                        registrationRequest.getEmail(),
                        registrationRequest.getPhoneNumber(),
                        registrationRequest.getPlace(),
                        registrationRequest.getNumber(),
                        registrationRequest.getStreet(),
                        registrationRequest.getCountry(),
                        false,
                        registrationRequest.getRegistrationRationale(),
                        registrationRequest.getBiography(),
                        role,
                        new ArrayList<Adventure>()
                );
                fishingInstructorService.addFishingInstructor(fishingInstructor);
                response = "Uspesno ste poslali zahtev o registraciji";

                break;
            case "VACATION_HOUSE_OWNER":
                VacationHouseOwner vacationHouseOwner = new VacationHouseOwner(
                        registrationRequest.getPassword(),
                        registrationRequest.getFirstName(),
                        registrationRequest.getLastName(),
                        registrationRequest.getEmail(),
                        registrationRequest.getPhoneNumber(),
                        registrationRequest.getPlace(),
                        registrationRequest.getNumber(),
                        registrationRequest.getStreet(),
                        registrationRequest.getCountry(),
                        false,
                        registrationRequest.getRegistrationRationale(),
                        role
                );
                vacationHouseOwnerService.addOwner(vacationHouseOwner);
                response = "Uspesno ste poslali zahtev o registraciji";

                break;
            case "BOAT_OWNER":
                BoatOwner boatOwner = new BoatOwner(
                        registrationRequest.getPassword(),
                        registrationRequest.getFirstName(),
                        registrationRequest.getLastName(),
                        registrationRequest.getEmail(),
                        registrationRequest.getPhoneNumber(),
                        registrationRequest.getPlace(),
                        registrationRequest.getNumber(),
                        registrationRequest.getStreet(),
                        registrationRequest.getCountry(),
                        false,
                        registrationRequest.getRegistrationRationale(),
                        new ArrayList<Boat>(),
                        role
                );
                boatOwnerService.addClient(boatOwner);
                response = "Uspesno ste poslali zahtev o registraciji";

                break;
        }
        return response;
    }

    public RegistrationRequest addRegistrationRequest(RegistrationRequest registrationRequest) {
        return registrationRequestRepository.save(registrationRequest);
    }

    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));
        if (confirmationToken.getConfirmedAt() != null) {
            return "Vas email je vec verifikovan"; //da se ne baca exception nego nesto drguo da se javi
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            return "Vas verifikacioni token je istekao";
//            throw new IllegalStateException("token expired");   //da se ne baca exception nego nesto drguo da se javi
        }
        confirmationTokenService.setConfirmedAt(token);
        User user = (User) userServiceSecurity.loadUserByUsername(confirmationToken.getUser().getEmail());
        user.setEnabled(true);
        userServiceSecurity.addClient((Client) user);

        return "Vasa verifikacija je uspesna";
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
