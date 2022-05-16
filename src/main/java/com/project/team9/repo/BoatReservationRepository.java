package com.project.team9.repo;

import com.project.team9.model.reservation.BoatReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoatReservationRepository extends JpaRepository<BoatReservation, Long> {
}
