package com.project.team9.repo;

import com.project.team9.model.reservation.BoatReservation;
import com.project.team9.model.reservation.VacationHouseReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoatReservationRepository extends JpaRepository<BoatReservation, Long> {

    @Query("FROM BoatReservation WHERE isQuickReservation = false AND isBusyPeriod = false")
    List<BoatReservation> findStandardReservations();

    @Query("FROM BoatReservation  WHERE isQuickReservation = false AND resource.id = ?1 ")
    List<BoatReservation> findPossibleCollisionReservations(Long resourceId);
}
