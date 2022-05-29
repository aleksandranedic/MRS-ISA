package com.project.team9.service;

import com.project.team9.model.reservation.VacationHouseReservation;
import com.project.team9.repo.VacationHouseReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacationHouseReservationService {
    private final VacationHouseReservationRepository repository;

    @Autowired
    public VacationHouseReservationService(VacationHouseReservationRepository repository) {
        this.repository = repository;
    }

    public void addReservation(VacationHouseReservation reservation) {
        this.repository.save(reservation);
    }
    public VacationHouseReservation getVacationHouseReservation(Long id) {
        return repository.getById(id);
    }
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<VacationHouseReservation> getAll() {
        return repository.findAll();
    }

    public List<VacationHouseReservation> getStandardReservations() {
        return repository.findStandardReservations();
    }

    public List<VacationHouseReservation> getPossibleCollisionReservations(Long id) {
        return repository.findPossibleCollisionReservations(id);
    }

    public Long save(VacationHouseReservation reservation) {
        return repository.save(reservation).getId();
    }

    public List<VacationHouseReservation> getBusyPeriodForVacationHouse(Long id) {
        return repository.findBusyPeriodForVacationHouse(id);
    }

    @Query("FROM VacationHouseReservation WHERE resource.id=?1 AND client.id = ?2")
    public boolean clientHasReservations(Long resourceId, Long clientId) {
        return repository.findReservationsForClient(resourceId, clientId).size() > 0;
    }
}
