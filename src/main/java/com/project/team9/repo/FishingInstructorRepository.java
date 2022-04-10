package com.project.team9.repo;

import com.project.team9.model.user.vendor.FishingInstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishingInstructorRepository extends JpaRepository<FishingInstructor, Long> {
}
