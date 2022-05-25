package com.project.team9.controller;

import com.project.team9.dto.ClientReviewDTO;
import com.project.team9.dto.ReviewScoresDTO;
import com.project.team9.model.review.ClientReview;
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
    public List<ClientReviewDTO> getReviews(@PathVariable String id) {
        return reviewService.getReviews(Long.parseLong(id));
    }

    @GetMapping(value = "getReviewScores/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReviewScoresDTO getReviewScores(@PathVariable String id) {
        return reviewService.getReviewScores(Long.parseLong(id));
    }

    @PostMapping("/add")
    public Long addReview(@RequestBody ClientReview review){
        return reviewService.sendReviewRequest(review);

    }
}
