package com.project.team9.service;

import com.project.team9.model.reservation.AdventureReservation;
import com.project.team9.repo.AdventureReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdventureReservationService {

    private final AdventureReservationRepository repository;

    @Autowired
    public AdventureReservationService(AdventureReservationRepository repository) {
        this.repository = repository;
    }

    public List<AdventureReservation> getStandardReservations() {
        return repository.findStandardReservations();
    }

    public List<AdventureReservation> getPossibleCollisionReservations(Long resourceId, Long ownerId) {
        return repository.findPossibleCollisionReservations(resourceId, ownerId);
    }

    public void save(AdventureReservation reservation) {
        repository.save(reservation);
    }

    public AdventureReservation getById(Long reservationID) {
        return repository.getById(reservationID);
    }
}
