package com.project.team9.repo;

import com.project.team9.model.reservation.AdventureReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdventureReservationRepository extends JpaRepository<AdventureReservation, Long> {
}
