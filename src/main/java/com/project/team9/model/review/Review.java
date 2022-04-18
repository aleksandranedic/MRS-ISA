package com.project.team9.model.review;

import javax.persistence.*;

@MappedSuperclass
public abstract class Review {
    @Id
    @SequenceGenerator(
            name="review_sequence",
            sequenceName = "review_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator="review_sequence"
    )
    private Long id;

    private Long resourceId;
    private Long vendorId;

    private int rating;
    private String text;

    public Review() {
    }

    public Review(Long resourceId, Long vendorId, int rating, String text) {
        this.resourceId = resourceId;
        this.vendorId = vendorId;
        this.rating = rating;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long ownerId) {
        this.vendorId = ownerId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
