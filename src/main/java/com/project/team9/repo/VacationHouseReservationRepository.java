package com.project.team9.repo;

import com.project.team9.model.reservation.AdventureReservation;
import com.project.team9.model.reservation.VacationHouseReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationHouseReservationRepository extends JpaRepository<VacationHouseReservation, Long> {

    @Query("FROM VacationHouseReservation WHERE isQuickReservation = false AND isBusyPeriod = false")
    List<VacationHouseReservation> findStandardReservations();

    @Query("FROM VacationHouseReservation  WHERE isQuickReservation = false AND resource.id = ?1 ")
    List<VacationHouseReservation> findPossibleCollisionReservations(Long resourceId);
}
