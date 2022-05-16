package com.project.team9.service;

import com.project.team9.dto.ReservationDTO;
import com.project.team9.model.Tag;
import com.project.team9.model.reservation.AdventureReservation;
import com.project.team9.model.reservation.Appointment;
import com.project.team9.model.resource.Adventure;
import com.project.team9.model.user.Client;
import com.project.team9.repo.AdventureReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AdventureReservationService {

    private final AdventureReservationRepository repository;
    private final AdventureService adventureService;
    private final ClientService clientService;

    @Autowired
    public AdventureReservationService(AdventureReservationRepository repository, AdventureService adventureService, ClientService clientService) {
        this.repository = repository;
        this.adventureService = adventureService;
        this.clientService = clientService;
    }

    public List<AdventureReservation> getReservationsForAdventure(Long id) {
        List<AdventureReservation> reservations = new ArrayList<AdventureReservation>();

        for (AdventureReservation ar : repository.findAll()) {
            if (Objects.equals(ar.getResource().getId(), id)) {
                reservations.add(ar);
            }
        }
        return reservations;
    }

    public List<AdventureReservation> getReservationsForFishingInstructor(Long id) {
        List<AdventureReservation> reservations = new ArrayList<AdventureReservation>();

        for (Adventure a : adventureService.findAdventuresWithOwner(id.toString())) {
            for (AdventureReservation ar : repository.findAll()) {
                if (Objects.equals(ar.getResource().getId(), a.getId())) {
                    reservations.add(ar);
                }
            }
        }

        return reservations;
    }

    public Long createReservation(ReservationDTO dto) {
        AdventureReservation reservation = createFromDTO(dto);
        repository.save(reservation);
        return reservation.getId();
    }

    private AdventureReservation createFromDTO(ReservationDTO dto) {
        List<Appointment> appointments = new ArrayList<Appointment>();

        LocalDateTime startTime = dto.getStartTime();
        LocalDateTime endTime = startTime.plusHours(1);

        while (startTime.isBefore(dto.getEndTime())) {
            appointments.add(new Appointment(startTime, endTime));
            startTime = endTime;
            endTime = startTime.plusHours(1);
        }

        Client client = clientService.getById(dto.getClientId().toString());
        Adventure adventure = adventureService.getById(dto.getResourceId().toString());

        return new AdventureReservation(
                appointments,
                dto.getNumberOfClients(),
                new ArrayList<Tag>(),
                dto.getPrice(),
                client,
                adventure,
                dto.isBusyPeriod(), dto.isQuickReservation());
    }


}
