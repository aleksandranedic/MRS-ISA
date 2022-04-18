package com.project.team9.model.request;

import javax.persistence.Entity;

@Entity
public class Complaint extends Request{

    public Complaint() {
    }

    public Complaint(String text, String response) {
        super(text, response);
    }
}
