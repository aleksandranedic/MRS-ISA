package com.project.team9.service;

import com.project.team9.model.request.VendorReviewRequest;
import com.project.team9.repo.VendorReviewRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorReviewRequestService {

    private final VendorReviewRequestRepository repository;

    @Autowired
    public VendorReviewRequestService(VendorReviewRequestRepository vendorReviewRequestRepository) {
        this.repository = vendorReviewRequestRepository;
    }

    public Long addVendorReviewRequest(VendorReviewRequest vendorReviewRequest) {
        return this.repository.save(vendorReviewRequest).getId();
    }

    public boolean reservationHasReviewRequest(Long id) {

        return this.repository.findReviewForReservation(id).size() > 0;
    }
}
