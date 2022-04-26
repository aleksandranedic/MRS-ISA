package com.project.team9.repo;

import com.project.team9.model.user.vendor.FishingInstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FishingInstructorRepository extends JpaRepository<FishingInstructor, Long> {
    Optional<FishingInstructor> findByEmail(String email);
}
