package com.project.team9.model.request;

import com.project.team9.model.review.VendorReview;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class ReviewRequest extends Request{

    @OneToOne
    private VendorReview review;

    public ReviewRequest() {
    }

    public ReviewRequest(String text, String response, VendorReview review) {
        super(text, response);
        this.review = review;
    }

    public VendorReview getReview() {
        return review;
    }

    public void setReview(VendorReview review) {
        this.review = review;
    }
}
