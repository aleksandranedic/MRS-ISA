package com.project.team9.dto;

public class VendorRegistrationRequestReplay {
    private String type;
    private String username;
    private String requestId;
    private String response;

    public VendorRegistrationRequestReplay(String type, String id, String requestId, String response) {
        this.type = type;
        this.username = id;
        this.requestId = requestId;
        this.response = response;
    }

    public VendorRegistrationRequestReplay() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
