package com.project.team9.service;

import com.project.team9.dto.VendorRegistrationRequestReplay;
import com.project.team9.model.Address;
import com.project.team9.model.request.RegistrationRequest;
import com.project.team9.model.resource.Boat;
import com.project.team9.model.user.Role;
import com.project.team9.model.user.vendor.BoatOwner;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.repo.FishingInstructorRepository;
import com.project.team9.security.PasswordEncoder;
import com.project.team9.security.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VendorRegistrationService {
    private final RegistrationRequestService service;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final FishingInstructorService fishingInstructorService;
    private final VacationHouseOwnerService vacationHouseOwnerService;
    private final BoatOwnerService boatOwnerService;
    private final AddressService addressService;
    private final EmailSender emailSender;

    @Autowired
    public VendorRegistrationService(RegistrationRequestService service, RoleService roleService, PasswordEncoder passwordEncoder, FishingInstructorService fishingInstructorService, VacationHouseOwnerService vacationHouseOwnerService, BoatOwnerService boatOwnerService, FishingInstructorRepository fishingInstructorRepository, AddressService addressService, EmailSender emailSender) {
        this.service = service;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.fishingInstructorService = fishingInstructorService;
        this.vacationHouseOwnerService = vacationHouseOwnerService;
        this.boatOwnerService = boatOwnerService;
        this.addressService = addressService;
        this.emailSender = emailSender;
    }

    public List<RegistrationRequest> getAllVendorRegistrations() {
        return service.getAllUndeletedRegistrationRequests();
    }

    public String validateVendor(VendorRegistrationRequestReplay replay) {
        RegistrationRequest registrationRequest = service.getRegistrationRequest(replay.getRequestId());
        if (registrationRequest == null) {
            return "Odobravanje registracija nije uspešno";
        } else if (fishingInstructorService.getFishingInstructorByEmail(replay.getUsername()) != null
                || vacationHouseOwnerService.getVacationHouseOwnerByEmail(replay.getUsername()) != null
                || boatOwnerService.getBoatOwnerByEmail(replay.getUsername()) != null) {
            return "Коrisnik sa unetim emailom vec postoji";
        } else {
            Address requestAddress = new Address(registrationRequest.getPlace(),
                    registrationRequest.getNumber(),
                    registrationRequest.getStreet(),
                    registrationRequest.getCountry());

            Role role = roleService.findRoleByName(registrationRequest.getUserRole());
            if (role == null) {
                role = new Role(registrationRequest.getUserRole());
                roleService.save(role);
            }
            Address address = addressService.getByAttributes(requestAddress);
            if (address == null) {
                addressService.addAddress(requestAddress);
            } else {
                requestAddress = address;
            }
            switch (registrationRequest.getUserRole()) {
                case "FISHING_INSTRUCTOR":
                    FishingInstructor fishingInstructor = new FishingInstructor(
                            passwordEncoder.bCryptPasswordEncoder().encode(registrationRequest.getPassword()),
                            registrationRequest.getFirstName(),
                            registrationRequest.getLastName(),
                            registrationRequest.getEmail(),
                            registrationRequest.getPhoneNumber(),
                            requestAddress,
                            false,
                            registrationRequest.getRegistrationRationale(),
                            registrationRequest.getBiography(),
                            role,
                            new ArrayList<>()
                    );
                    fishingInstructor.setEnabled(true);
                    fishingInstructor.setLastPasswordResetDate(new Timestamp(new Date().getTime()));
                    fishingInstructorService.addFishingInstructor(fishingInstructor);
                    break;
                case "VACATION_HOUSE_OWNER":
                    VacationHouseOwner vacationHouseOwner = new VacationHouseOwner(
                            passwordEncoder.bCryptPasswordEncoder().encode(registrationRequest.getPassword()),
                            registrationRequest.getFirstName(),
                            registrationRequest.getLastName(),
                            registrationRequest.getEmail(),
                            registrationRequest.getPhoneNumber(),
                            requestAddress,
                            false,
                            registrationRequest.getRegistrationRationale(),
                            role
                    );
                    vacationHouseOwner.setEnabled(true);
                    vacationHouseOwner.setLastPasswordResetDate(new Timestamp(new Date().getTime()));
                    vacationHouseOwnerService.addOwner(vacationHouseOwner);
                    break;
                case "BOAT_OWNER":
                    BoatOwner boatOwner = new BoatOwner(
                            passwordEncoder.bCryptPasswordEncoder().encode(registrationRequest.getPassword()),
                            registrationRequest.getFirstName(),
                            registrationRequest.getLastName(),
                            registrationRequest.getEmail(),
                            registrationRequest.getPhoneNumber(),
                            requestAddress,
                            false,
                            registrationRequest.getRegistrationRationale(),
                            new ArrayList<Boat>(),
                            role
                    );
                    boatOwner.setLastPasswordResetDate(new Timestamp(new Date().getTime()));
                    boatOwner.setEnabled(true);
                    boatOwnerService.save(boatOwner);
                    break;
            }

            registrationRequest.setResponse(replay.getResponse());
            registrationRequest.setDeleted(true);
            service.addRegistrationRequest(registrationRequest);
            emailSender.send(
                    registrationRequest.getEmail(),
                    buildEmail(registrationRequest.getFirstName() + " " + registrationRequest.getLastName()));
            return "Odobravanje registracije je uspešno";
        }
    }

    public String deleteRegistrationRequest(VendorRegistrationRequestReplay replay) {
        RegistrationRequest registrationRequest = service.getRegistrationRequest(replay.getRequestId());
        registrationRequest.setResponse(replay.getResponse());
        return service.deleteRegistrationRequest(replay.getRequestId());
    }

    private String buildEmail(String name) {
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
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Uspesno ste registrovani. </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + "http://localhost:3000/login" + "\">Go to login</a> </p></blockquote>\n  <p>See you soon</p>" +
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
