package com.project.team9.repo;

import com.project.team9.model.resource.Adventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdventureRepository extends JpaRepository<Adventure, Long> {

    @Query("FROM Adventure WHERE owner.id = ?1")
    List<Adventure> findAdventuresWithOwner(Long ownerId);
    List<Adventure> findByOwnerId(Long owner_id);
}
