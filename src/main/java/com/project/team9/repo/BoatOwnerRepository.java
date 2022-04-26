package com.project.team9.repo;

import com.project.team9.model.user.vendor.BoatOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoatOwnerRepository extends JpaRepository<BoatOwner,Long> {
    Optional<BoatOwner> findByEmail(String email);
}
