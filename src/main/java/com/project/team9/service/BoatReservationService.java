package com.project.team9.service;

import com.project.team9.model.reservation.BoatReservation;
import com.project.team9.repo.BoatReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoatReservationService {
    private final BoatReservationRepository repository;

    @Autowired
    public BoatReservationService(BoatReservationRepository repository) {
        this.repository = repository;
    }

    public void addReservation(BoatReservation reservation) {
        this.repository.save(reservation);
    }
    public BoatReservation getBoatReservation(Long id) {
        return repository.getById(id);
    }
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<BoatReservation> getAll() {
        return repository.findAll();
    }

    public List<BoatReservation> getStandardReservations() {
        return repository.findStandardReservations();
    }

    public List<BoatReservation> getPossibleCollisionReservations(Long id){
        return repository.findPossibleCollisionReservations(id);
    }

    public Long save(BoatReservation reservation) {
        repository.save(reservation);
        return null;
    }

    public List<BoatReservation> getBusyPeriodForBoat(Long id) {
        return repository.findBusyPeriodForBoat(id);
    }

    public boolean hasReservations(Long resourceId, Long clientId) {
        return repository.findReservationsForClient(resourceId, clientId).size() > 0;
    }


    public boolean clientCanReviewVendor(Long vendorId, Long clientId) {
        return repository.findReservationsForClientAndVendor(vendorId, clientId).size()>0;
    }
}
