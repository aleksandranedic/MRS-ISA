package com.project.team9.repo;

import com.project.team9.model.request.ReviewRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ReviewRequestRepository extends JpaRepository<ReviewRequest, Long> {
    @Query("FROM ReviewRequest WHERE resourceId = ?1 AND clientId = ?2")
    List<ReviewRequest> findReviewRequests(Long resourceId, Long clientId);
}
