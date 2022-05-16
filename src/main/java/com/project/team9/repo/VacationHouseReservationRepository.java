package com.project.team9.repo;

import com.project.team9.model.reservation.VacationHouseReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationHouseReservationRepository extends JpaRepository<VacationHouseReservation, Long> {
}
