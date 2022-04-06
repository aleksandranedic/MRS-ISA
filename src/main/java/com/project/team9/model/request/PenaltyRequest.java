package com.project.team9.model.request;

import com.project.team9.model.review.ClientReview;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class PenaltyRequest extends Request{
    @OneToOne
    private ClientReview review;

    public PenaltyRequest() {
    }

    public PenaltyRequest(String text, String response, ClientReview review) {
        super(text, response);

    }

    public ClientReview getReview() {
        return review;
    }

    public void setReview(ClientReview review) {
        this.review = review;
    }
}
