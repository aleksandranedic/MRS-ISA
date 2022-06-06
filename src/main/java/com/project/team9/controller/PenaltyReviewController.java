package com.project.team9.controller;

import com.project.team9.dto.VendorRequestReviewDenialDTO;
import com.project.team9.dto.VendorReviewDTO;
import com.project.team9.service.PenaltyReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "penaltyReview")
public class PenaltyReviewController {
    private final PenaltyReviewService reviewService;

    @Autowired
    public PenaltyReviewController(PenaltyReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<VendorReviewDTO>> getPenaltyRequests(){
        return  ResponseEntity.ok(reviewService.getPenaltyReviews());
    }
    @PostMapping
    public ResponseEntity<String> createVendorReview(@RequestBody VendorReviewResponseDTO reviewResponseDTO){
        return ResponseEntity.ok(reviewService.createVendorReview(reviewResponseDTO));
    }
    @PostMapping(path = "deny")
    public ResponseEntity<String> createVendorReview(@RequestBody VendorRequestReviewDenialDTO vendorRequestReviewDenial){
        return ResponseEntity.ok(reviewService.denyReview(vendorRequestReviewDenial));
    }
}
