package com.project.team9.model.request;

import com.project.team9.model.user.User;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DeleteRequest extends Request{
    private Long user_id;

    public DeleteRequest() {
    }

    public DeleteRequest(String text, String response, Long user_id) {
        super(text, response);
        this.user_id = user_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
