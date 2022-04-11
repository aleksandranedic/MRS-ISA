package com.project.team9.repo;

import com.project.team9.model.resource.VacationHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationHouseRepository extends JpaRepository<VacationHouse, Long> {
}
