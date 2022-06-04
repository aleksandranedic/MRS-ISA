package com.project.team9.controller;

import com.project.team9.dto.ClientReviewDTO;
import com.project.team9.dto.ReviewScoresDTO;
import com.project.team9.model.review.ClientReview;
import com.project.team9.model.review.VendorReview;
import com.project.team9.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(value = "getReviews/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClientReviewDTO> getResourcesReviews(@PathVariable String id) {
        return reviewService.getResourceReviews(Long.parseLong(id));
    }
    @GetMapping(value = "getVendorReviews/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClientReviewDTO> getVendorReviews(@PathVariable String id) {
        return reviewService.getVendorReviews(Long.parseLong(id));
    }

    @GetMapping(value = "getReviewScores/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReviewScoresDTO getReviewScores(@PathVariable String id) {
        return reviewService.getReviewScores(Long.parseLong(id), "resource");
    }

    @GetMapping(value = "getVendorReviewScores/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReviewScoresDTO getVendorReviewScores(@PathVariable String id) {
        return reviewService.getReviewScores(Long.parseLong(id), "vendor");
    }

    @PostMapping("/add")
    public Long addReview(@RequestBody ClientReview review){
        return reviewService.sendReviewRequest(review);

    }

    @GetMapping(value = "getRating/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public double getRating(@PathVariable String id) {
        return reviewService.getRating(Long.parseLong(id), "resource");
    }

    @GetMapping(value = "getVendorRating/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public double getVendorRating(@PathVariable String id) {
        return reviewService.getRating(Long.parseLong(id), "vendor");
    }

    @PostMapping("/vendor/add")
    public Long addVendorReview(@RequestBody VendorReview vendorReview) {
        return reviewService.sendVendorReviewRequest(vendorReview);
    }
}
