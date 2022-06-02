package com.project.team9.service;

import com.project.team9.dto.DeleteReplayDTO;
import com.project.team9.model.request.DeleteRequest;
import com.project.team9.model.user.Client;
import com.project.team9.model.user.vendor.BoatOwner;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.repo.DeleteRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeleteRequestService {
    private final DeleteRequestRepository deleteRequestRepository;
    private final FishingInstructorService fishingInstructorService;
    private final ClientService clientService;
    private final BoatOwnerService boatOwnerService;
    private final EmailService emailService;
    private final VacationHouseOwnerService vacationHouseOwnerService;
    private final AdventureReservationService adventureReservationService;
    private final BoatReservationService boatReservationService;
    private final VacationHouseReservationService vacationHouseReservationService;

    @Autowired
    public DeleteRequestService(DeleteRequestRepository deleteRequestRepository, FishingInstructorService fishingInstructorService, ClientService clientService, BoatOwnerService boatOwnerService, EmailService emailService, VacationHouseOwnerService vacationHouseOwnerService, AdventureReservationService adventureReservationService, BoatReservationService boatReservationService, VacationHouseReservationService vacationHouseReservationService) {
        this.deleteRequestRepository = deleteRequestRepository;
        this.fishingInstructorService = fishingInstructorService;
        this.clientService = clientService;
        this.boatOwnerService = boatOwnerService;
        this.emailService = emailService;
        this.vacationHouseOwnerService = vacationHouseOwnerService;
        this.adventureReservationService = adventureReservationService;
        this.boatReservationService = boatReservationService;
        this.vacationHouseReservationService = vacationHouseReservationService;
    }

    public void addDeleteRequest(DeleteRequest deleteRequest) {
        deleteRequestRepository.save(deleteRequest);
    }

    public List<DeleteRequest> getAllDeletionRequests() {
        return deleteRequestRepository.findAll().stream().filter(deleteRequest -> !deleteRequest.getDeleted()).collect(Collectors.toCollection(ArrayList::new));
    }

    public DeleteRequest getById(String requestId) {
        return deleteRequestRepository.getById(Long.parseLong(requestId));
    }

    public String processDeletionRequest(DeleteReplayDTO deleteReplayDTO) {
        String response = "";
        String email = "";
        String fullName = "";
        if (deleteReplayDTO.getType().equals("approve")) {
            if (fishingInstructorService.getFishingInstructorByEmail(deleteReplayDTO.getUsername()) != null) {
                FishingInstructor fishingInstructor = fishingInstructorService.getFishingInstructorByEmail(deleteReplayDTO.getUsername());
                fishingInstructor.setDeleted(true);
                fishingInstructorService.addFishingInstructor(fishingInstructor);
                email = fishingInstructor.getEmail();
                fullName = fishingInstructor.getFirstName() + " " + fishingInstructor.getLastName();
                int numberOfReservation=adventureReservationService.getAdventureReservationsForVendorId(fishingInstructor.getId()).size();
                if(numberOfReservation>0){
                    return "Nalog ne može da se obriše pošto instruktor pecanja ima zakazane rezervacije";
                }
            } else if (boatOwnerService.getBoatOwnerByEmail(deleteReplayDTO.getUsername()) != null) {
                BoatOwner boatOwner = boatOwnerService.getBoatOwnerByEmail(deleteReplayDTO.getUsername());
                boatOwner.setDeleted(true);
                boatOwnerService.save(boatOwner);
                email = boatOwner.getEmail();
                fullName = boatOwner.getFirstName() + " " + boatOwner.getLastName();
                int numberOfReservation=boatReservationService.getBoatReservationsForVendorId(boatOwner.getId()).size();
                if(numberOfReservation>0){
                    return "Nalog ne može da se obriše pošto vlasnik broda ima zakazane rezervacije";
                }
            } else if (vacationHouseOwnerService.getVacationHouseOwnerByEmail(deleteReplayDTO.getUsername()) != null) {
                VacationHouseOwner vacationHouseOwner = vacationHouseOwnerService.getVacationHouseOwnerByEmail(deleteReplayDTO.getUsername());
                vacationHouseOwner.setDeleted(true);
                vacationHouseOwnerService.save(vacationHouseOwner);
                email = vacationHouseOwner.getEmail();
                fullName = vacationHouseOwner.getFirstName() + " " + vacationHouseOwner.getLastName();
                int numberOfReservation=vacationHouseReservationService.getVacationHouseReservationsForVendorId(vacationHouseOwner.getId()).size();
                if(numberOfReservation>0){
                    return "Nalog ne može da se obriše pošto vlasnik vikendice ima zakazane rezervacije";
                }

            } else if (clientService.getClientByEmail(deleteReplayDTO.getUsername()) != null) {
                Client client = clientService.getClientByEmail(deleteReplayDTO.getUsername());
                client.setDeleted(true);
                clientService.addClient(client);
                email = client.getEmail();
                fullName = client.getFirstName() + " " + client.getLastName();
                int numberOfReservation=vacationHouseReservationService.getVacationHouseReservationsForClienId(client.getId()).size()+
                                            adventureReservationService.getAdventureReservationsForClientId(client.getId()).size()+
                                            boatReservationService.getBoatReservationsForClientId(client.getId()).size();
                if(numberOfReservation>0){
                    return "Nalog ne može da se obriše pošto klijent ima zakazane rezervacije";
                }
            }
            response = "Uspešno ste obrisali korisnika";
            emailService.send(email, buildEmail(fullName));
        } else {
            response = "Uspešno ste odbili brisanje korisnika";
        }
        DeleteRequest deleteRequest = getById(deleteReplayDTO.getRequestId());
        deleteReplayDTO.setComment(deleteReplayDTO.getComment());
        deleteRequest.setDeleted(true);
        addDeleteRequest(deleteRequest);
        return response;
    }

    private String buildEmail(String fullName) {
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
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Obavestenje o brisanju naloga</span>\n" +
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
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Zdravo " + fullName + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Vaš nalog je uspešno obrisan.</p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">  </p></blockquote>\n " +
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
