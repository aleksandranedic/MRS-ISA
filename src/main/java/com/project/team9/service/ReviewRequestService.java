package com.project.team9.service;

import com.project.team9.model.request.ReviewRequest;
import com.project.team9.model.request.VendorReviewRequest;
import com.project.team9.repo.ReviewRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewRequestService {
    private final ReviewRequestRepository repository;

    public ReviewRequestService(ReviewRequestRepository repository) {
        this.repository = repository;
    }

    public Long addRequest(ReviewRequest reviewRequest) {
        return repository.save(reviewRequest).getId();
    }

    public boolean hasReviewRequests(Long resourceId, Long clientId) {
        return repository.findReviewRequests(resourceId, clientId).size() > 0;
    }

}
