package com.project.team9.service;

import com.project.team9.dto.ComplaintDTO;
import com.project.team9.dto.ComplaintResponseDTO;
import com.project.team9.model.request.Complaint;
import com.project.team9.model.user.Client;
import com.project.team9.repo.ComplaintsRepository;
import com.project.team9.security.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplaintsService {
    private final ComplaintsRepository repository;
    private final ClientService clientService;
    private final FishingInstructorService fishingInstructorService;
    private final BoatOwnerService boatOwnerService;
    private final VacationHouseOwnerService vacationHouseOwnerService;
    private final AdventureService adventureService;
    private final BoatService boatService;
    private final VacationHouseService vacationHouseService;
    private final EmailSender emailSender;
    @Autowired
    public ComplaintsService(ComplaintsRepository repository, ClientService clientService, FishingInstructorService fishingInstructorService, BoatOwnerService boatOwnerService, VacationHouseOwnerService vacationHouseOwnerService, AdventureService adventureService, BoatService boatService, VacationHouseService vacationHouseService, EmailSender emailSender) {
        this.repository = repository;
        this.clientService = clientService;
        this.fishingInstructorService = fishingInstructorService;
        this.boatOwnerService = boatOwnerService;
        this.vacationHouseOwnerService = vacationHouseOwnerService;
        this.adventureService = adventureService;
        this.boatService = boatService;
        this.vacationHouseService = vacationHouseService;
        this.emailSender = emailSender;
    }

    public void deleteComplaint(Long id){
        Complaint complaint=repository.getById(id);
        complaint.setDeleted(true);
        repository.save(complaint);
    }

    public List<ComplaintDTO> getAllComplaints() {
        List<ComplaintDTO> complaintDTOS=new ArrayList<>();
        for (Complaint complaint :
                repository.findAll()) {

            complaintDTOS.add(new ComplaintDTO(
                    complaint.getUserId(),
                    complaint.getEntityType(),
                    complaint.getEntityId(),
                    getEntityName(complaint.getEntityType(), complaint.getEntityId()),
                    getUserFullName(complaint.getUserId()),
                    complaint.getComment(),
                    complaint.getId()
            ));
        }
        return complaintDTOS;
    }
    private String getUserFullName(Long id){
        return clientService.getById(String.valueOf(id)).getName();
    }

    private String getEntityName(String entityType,Long entityId){
        switch (entityType){
            case "VACATION_HOUSE_OWNER":
                return vacationHouseOwnerService.getOwner(entityId).getName();
            case "FISHING_INSTRUCTOR":
                return fishingInstructorService.getById(String.valueOf(entityId)).getName();
            case "BOAT_OWNER":
                return  boatOwnerService.getOwner(entityId).getName();
            case "VACATION_HOUSE":
                return vacationHouseService.getVacationHouse(entityId).getTitle();
            case "BOAT":
                return boatService.getBoat(entityId).getTitle();
            case "ADVENTURE":
                return adventureService.getById(String.valueOf(entityId)).getTitle();
        }
        return "";
    }

    public String answerComplaint(ComplaintResponseDTO responseDTO) {
        deleteComplaint(responseDTO.getComplaintId());
        String fullName="";
        String email="";
        switch (responseDTO.getEntityType()){
            case "VACATION_HOUSE_OWNER":
                fullName=vacationHouseOwnerService.getOwner(responseDTO.getEntityId()).getName();
                email=vacationHouseOwnerService.getOwner(responseDTO.getEntityId()).getEmail();
            case "FISHING_INSTRUCTOR":
                fullName=fishingInstructorService.getById(String.valueOf(responseDTO.getEntityId())).getName();
                email=fishingInstructorService.getById(String.valueOf(responseDTO.getEntityId())).getEmail();
            case "BOAT_OWNER":
                fullName=boatOwnerService.getOwner(responseDTO.getEntityId()).getName();
                email=boatOwnerService.getOwner(responseDTO.getEntityId()).getEmail();
            case "VACATION_HOUSE":
                fullName=vacationHouseService.getVacationHouse(responseDTO.getEntityId()).getOwner().getName();
                email=vacationHouseService.getVacationHouse(responseDTO.getEntityId()).getOwner().getEmail();
            case "BOAT":
                fullName=boatService.getBoat(responseDTO.getEntityId()).getOwner().getName();
                email=boatService.getBoat(responseDTO.getEntityId()).getOwner().getEmail();
            case "ADVENTURE":
                fullName=adventureService.getById(String.valueOf(responseDTO.getEntityId())).getOwner().getName();
                email=adventureService.getById(String.valueOf(responseDTO.getEntityId())).getOwner().getEmail();
        }
        Client client= clientService.getById(String.valueOf(responseDTO.getUserId()));
        emailSender.send(email, buildEmail(fullName, responseDTO.getResponse()), "Odgovor na žalba");
        emailSender.send(client.getEmail(), buildEmail(client.getName(), responseDTO.getResponse()), "Odgovor na žalba");
        return "Uspešno ste odgovoroli na žalbu";
    }
    private String buildEmail(String fullName, String response) {
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
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Obavestenje o receniziji</span>\n" +
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
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Zdravo " + fullName + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Ovo je administratorov odgovor na recenziju: " + response + " </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> </p></blockquote>\n " +
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
