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
    public DeleteRequestService(DeleteRequestRepository deleteRequestRepository, FishingInstructorService fishingInstructorService, ClientService clientService, BoatOwnerService boatOwnerService, EmailService emailService, VacationHouseOwnerService vacationHouseOwnerService
            , AdventureReservationService adventureReservationService, BoatReservationService boatReservationService, VacationHouseReservationService vacationHouseReservationService
    ) {
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
            String fullResponse="Administratoreva poruka je: "+deleteReplayDTO.getComment()+"\nVaš nalog će biti obrisan";
            String emailForUser=emailService.buildHTMLEmail(fullName,fullResponse,"Hvala Vam što ste bili deo našeg tima","Potvrda o brisanje naloga");
            emailService.send(email, emailForUser,"Potvrda o brisanje naloga");
        } else {
            response = "Uspešno ste odbili brisanje korisnika";
        }
        DeleteRequest deleteRequest = getById(deleteReplayDTO.getRequestId());
        deleteReplayDTO.setComment(deleteReplayDTO.getComment());
        deleteRequest.setDeleted(true);
        addDeleteRequest(deleteRequest);
        return response;
    }

    public String deleteClient(Long id,String deletingReason, String UserType) {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setUserId(id);
        deleteRequest.setUserType(UserType);
        deleteRequest.setComment(deletingReason);
        deleteRequest.setResponse("");
        deleteRequestRepository.save(deleteRequest);
        return "Uspešno ste poslali zahtev za brisanje vašeg naloga";
    }
}
