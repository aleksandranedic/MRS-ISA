package com.project.team9.model.request;

import javax.persistence.*;

@MappedSuperclass
public abstract class Request {
    @Id
    @SequenceGenerator(
            name="request_sequence",
            sequenceName = "request_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator="request_sequence"
    )
    private Long id;
    private String text;
    private String response;

    public Request() {
    }

    public Request(String text, String response) {
        this.text = text;
        this.response = response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
