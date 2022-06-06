package com.project.team9.service;

import com.project.team9.controller.VendorReviewResponseDTO;
import com.project.team9.dto.VendorRequestReviewDenialDTO;
import com.project.team9.dto.VendorReviewDTO;
import com.project.team9.model.request.VendorReviewRequest;
import com.project.team9.model.review.VendorReview;
import com.project.team9.model.user.Client;
import com.project.team9.repo.VendorReviewRepository;
import com.project.team9.security.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PenaltyReviewService {

    private final VendorReviewRequestService vendorReviewRequestService;
    private final ClientService clientService;
    private final AdventureService adventureService;
    private final BoatService boatService;
    private final VacationHouseService vacationHouseService;
    private final VendorReviewRepository vendorReviewRepository;
    private final EmailSender emailSender;

    @Autowired
    public PenaltyReviewService(VendorReviewRequestService vendorReviewRequestService, ClientService clientService, AdventureService adventureService, BoatService boatService, VacationHouseService vacationHouseService, VendorReviewRepository vendorReviewRepository, EmailSender emailSender) {
        this.vendorReviewRequestService = vendorReviewRequestService;
        this.clientService = clientService;
        this.adventureService = adventureService;
        this.boatService = boatService;
        this.vacationHouseService = vacationHouseService;
        this.vendorReviewRepository = vendorReviewRepository;
        this.emailSender = emailSender;
    }

    public List<VendorReviewDTO> getPenaltyReviews() {
        List<VendorReviewDTO> penalties = new ArrayList<>();
        for (VendorReviewRequest vendorReviewRequest : vendorReviewRequestService.getAllVendorReviews()) {
            String[] list = getResourceTitleVendorNameVendorId(vendorReviewRequest.getResourceId());
            penalties.add(new VendorReviewDTO(
                    vendorReviewRequest.getClientId(),
                    vendorReviewRequest.getVendorId(),
                    vendorReviewRequest.getReservationId(),
                    vendorReviewRequest.getResourceId(), list[0],
                    list[1],
                    vendorReviewRequest.getRating(),
                    clientService.getById(vendorReviewRequest.getClientId().toString()).getName(),
                    vendorReviewRequest.isPenalty(),
                    vendorReviewRequest.isNoShow(),
                    vendorReviewRequest.getComment(),
                    vendorReviewRequest.getId()));
        };
        return penalties;
    }

    private String[] getResourceTitleVendorNameVendorId(Long resourceId) {
        String[] list = new String[4];
        try {
            list[0] = adventureService.getById(resourceId.toString()).getTitle();
            list[1] = adventureService.getById(resourceId.toString()).getOwner().getName();
            list[2] = adventureService.getById(resourceId.toString()).getOwner().getId().toString();
            list[3] = adventureService.getById(resourceId.toString()).getOwner().getEmail();
        } catch (Exception e) {
            list[0] = "";
            list[1] = "";
            list[2] = "";
            list[3] = "";
        }
        if (list[0].equals("")) {
            try {
                list[0] = boatService.getBoat(resourceId).getTitle();
                list[1] = boatService.getBoat(resourceId).getOwner().getName();
                list[2] = boatService.getBoat(resourceId).getOwner().getId().toString();
                list[3] = boatService.getBoat(resourceId).getOwner().getEmail();
            } catch (Exception e) {
                list[0] = "";
                list[1] = "";
                list[2] = "";
                list[3] = "";
            }
        }
        if (list[0].equals("")) {
            try {
                list[0] = vacationHouseService.getVacationHouse(resourceId).getTitle();
                list[1] = vacationHouseService.getVacationHouse(resourceId).getOwner().getName();
                list[2] = vacationHouseService.getVacationHouse(resourceId).getOwner().getId().toString();
                list[3] = vacationHouseService.getVacationHouse(resourceId).getOwner().getEmail();
            } catch (Exception e) {
                list[0] = "";
                list[1] = "";
                list[2] = "";
                list[3] = "";
            }
        }
        return list;
    }

    public String createVendorReview(VendorReviewResponseDTO reviewResponseDTO) {
        String[] list = getResourceTitleVendorNameVendorId(reviewResponseDTO.getResourceId());
        VendorReview vendorReview = new VendorReview(
                reviewResponseDTO.getResourceId(),
                Long.parseLong(list[2]),
                reviewResponseDTO.getRating(),
                reviewResponseDTO.getText(),
                reviewResponseDTO.getClientId(),
                reviewResponseDTO.isPenalty(),
                reviewResponseDTO.isNoShow(),
                reviewResponseDTO.getReservationId(),
                reviewResponseDTO.getResponse()
        );
        vendorReviewRepository.save(vendorReview);

        vendorReviewRequestService.delete(reviewResponseDTO.getVendorReviewRequestId());

        //TODO proveri koliko klijent ima penala i postavi ga na enabled ili sta god
        Client client= clientService.getById(reviewResponseDTO.getClientId().toString());

        String penaltyVendorText = "";
        String penaltyClientText = "";
        if(reviewResponseDTO.isCheckPenalty() && reviewResponseDTO.isCheckNoShow())
        {
            client.setNumOfPenalties(client.getNumOfPenalties() + 2);
            penaltyVendorText = "Nalog klijenta koji je koristio jedan od vaših resursa će dobiti 2 penala.";
            penaltyClientText = "Administrator je odlučio da Vaš nalog dobije 2 penala za neprostojnost i odsutstvo";
        }
        else if (reviewResponseDTO.isCheckPenalty() ) {
            client.setNumOfPenalties(client.getNumOfPenalties() + 1);
            penaltyVendorText = "Nalog klijenta koji je koristio jedan od vaših resursa će dobiti penal";
            penaltyClientText = "Administrator je odlučio da Vaš nalog dobije penal";
        }
        else if (reviewResponseDTO.isCheckNoShow()) {
            client.setNumOfPenalties(client.getNumOfPenalties() + 1);
            penaltyVendorText = "Nalog klijenta koji je koristio jedan od vaših resursa će dobiti penal jer se nije pojavio";
            penaltyClientText = "Administrator je odlučio da Vaš nalog dobije penal za odsutstvo";
        }
        //posalji email vendoru
        emailSender.send(list[3], buildEmail(list[1], reviewResponseDTO.getResponse(), penaltyVendorText), "Recenzija");
        //posalji email klijentu
        emailSender.send(client.getEmail(), buildEmail(client.getName(),reviewResponseDTO.getResponse(), penaltyClientText),"Recenzija");
        return "Uspešno ste obradili receniziju pružilaca usluga";
    }

    private String buildEmail(String fullName, String response, String penaltyText) {
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
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Zdravo " + fullName + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Ovo je administratorov odgovor na recenziju: " + response + " </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> "+penaltyText+"</p></blockquote>\n " +
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

    public String denyReview(VendorRequestReviewDenialDTO denialDTO) {
        String[] list = getResourceTitleVendorNameVendorId(denialDTO.getResourceId());
        vendorReviewRequestService.delete(denialDTO.getVendorReviewRequestId());
        emailSender.send(list[3], buildEmail(list[1], denialDTO.getResponse(),"Recenzija je odbijena"), "Recenzija");
        return "Uspešno ste odbili recenziju";
    }
}
