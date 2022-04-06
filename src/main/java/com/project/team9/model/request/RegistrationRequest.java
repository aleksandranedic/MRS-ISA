package com.project.team9.model.request;

import javax.persistence.Entity;


@Entity
public class RegistrationRequest extends Request{

    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String place;
    private String number;
    private String street;
    private String country;
    String registrationType;
    String registrationRationale;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String text, String response, String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country, String registrationType, String registrationRationale) {
        super(text, response);
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.place = place;
        this.number = number;
        this.street = street;
        this.country = country;
        this.registrationType = registrationType;
        this.registrationRationale = registrationRationale;
    }
}
