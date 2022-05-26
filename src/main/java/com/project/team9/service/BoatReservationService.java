package com.project.team9.service;

import com.project.team9.model.reservation.BoatReservation;
import com.project.team9.model.reservation.VacationHouseReservation;
import com.project.team9.repo.BoatReservationRepository;
import com.project.team9.repo.VacationHouseReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void save(BoatReservation reservation) {
        repository.save(reservation);
    }

    public List<BoatReservation> getBusyPeriodForBoat(Long id) {
        return repository.findBusyPeriodForBoat(id);
    }

    public boolean hasReservations(Long resourceId, Long clientId) {
        return repository.findReservationsForClient(resourceId, clientId).size() > 0;
    }
}
