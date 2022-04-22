package com.project.team9.repo;

import com.project.team9.model.resource.VacationHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationHouseRepository extends JpaRepository<VacationHouse, Long> {
    List<VacationHouse> findByOwnerId(Long owner_id);
}
