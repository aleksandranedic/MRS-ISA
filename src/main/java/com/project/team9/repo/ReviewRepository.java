package com.project.team9.repo;

import com.project.team9.model.review.ClientReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ClientReview, Long> {
    List<ClientReview> findByResourceId(Long resource_id);
}
