package com.project.team9.model.review;

import javax.persistence.Entity;

@Entity
public class VendorReview extends Review{

    private Long vendorId;

    public VendorReview() {
    }

    public VendorReview(Long resourceId, Long vendorId, int rating, String text, Long vendorId1) {
        super(resourceId, vendorId, rating, text);
        this.vendorId = vendorId1;
    }
}
