package com.project.team9.repo;

import com.project.team9.model.resource.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoatRepository extends JpaRepository<Boat, Long> {
    List<Boat> findByOwnerId(Long owner_id);
}
