package com.project.team9.model.request;

import javax.persistence.Entity;

@Entity
public class DeleteRequest extends Request{
    private String userDeletionIdentification;
    private String userType;

    public DeleteRequest() {
    }

    public DeleteRequest(String text, String response,String userDeletionIdentification, String userType) {
        super(text, response);
        this.userDeletionIdentification = userDeletionIdentification;
        this.userType = userType;
    }

    public String getUserDeletionIdentification() {
        return userDeletionIdentification;
    }

    public void setUserDeletionIdentification(String userDeletionIdentification) {
        this.userDeletionIdentification = userDeletionIdentification;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
