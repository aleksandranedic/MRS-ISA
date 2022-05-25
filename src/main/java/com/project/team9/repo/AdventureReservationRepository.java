package com.project.team9.repo;

import com.project.team9.dto.AdventureDTO;
import com.project.team9.model.reservation.AdventureReservation;
import com.project.team9.model.resource.Adventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdventureReservationRepository extends JpaRepository<AdventureReservation, Long> {

    @Query("FROM AdventureReservation WHERE isQuickReservation = false AND isBusyPeriod = false")
    List<AdventureReservation> findStandardReservations();

    @Query("FROM AdventureReservation WHERE isBusyPeriod = true")
    List<AdventureReservation> findBusyPeriods();

    @Query("FROM AdventureReservation  WHERE isQuickReservation = false AND (resource.id = ?1 OR resource.owner.id = ?2)")
    List<AdventureReservation> findPossibleCollisionReservations(Long resourceId, Long ownerId);

    @Query("FROM AdventureReservation WHERE isBusyPeriod = true AND resource.owner.id = ?1")
    List<AdventureReservation> findBusyPeriodsForFishingInstructor(Long id);

    @Query("FROM AdventureReservation WHERE isBusyPeriod = true AND (resource.id = ?1 OR resource.owner.id = ?2)")
    List<AdventureReservation> findBusyPeriodsForAdventure(Long id, Long ownerId);

    @Query("FROM AdventureReservation WHERE resource.id=?1 AND client.id = ?2")
    List<AdventureReservation> findReservationsForClient(Long resourceId, Long clientId);
}
