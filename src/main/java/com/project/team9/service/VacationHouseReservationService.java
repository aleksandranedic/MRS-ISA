package com.project.team9.service;

import com.project.team9.model.reservation.VacationHouseReservation;
import com.project.team9.model.resource.VacationHouse;
import com.project.team9.repo.VacationHouseReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
