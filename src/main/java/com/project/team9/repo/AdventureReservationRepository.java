package com.project.team9.repo;

import com.project.team9.dto.AdventureDTO;
import com.project.team9.model.reservation.AdventureReservation;
import com.project.team9.model.resource.Adventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdventureReservationRepository extends JpaRepository<AdventureReservation, Long> {


}
