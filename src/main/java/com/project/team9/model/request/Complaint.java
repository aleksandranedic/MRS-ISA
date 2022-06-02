package com.project.team9.model.request;

import javax.persistence.Entity;

@Entity
public class Complaint extends Request{

    private Long userId;

    public Complaint() {
    }

    public Complaint(String text, String response, Long userId) {
        super(text, response);
        this.userId=userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
