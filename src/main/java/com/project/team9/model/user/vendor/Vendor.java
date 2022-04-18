package com.project.team9.model.user.vendor;

import com.project.team9.model.Address;
import com.project.team9.model.user.User;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Vendor extends User {
    RegistrationType registrationType;
    String registrationRationale;

    public Vendor() {
    }

    public Vendor(String password, String firstName, String lastName, String email, String phoneNumber, Address address, RegistrationType registrationType, String registrationRationale) {
        super(password, firstName, lastName, email, phoneNumber, address);
        this.registrationType = registrationType;
        this.registrationRationale = registrationRationale;
    }

    public Vendor(String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country, RegistrationType registrationType, String registrationRationale) {
        super(password, firstName, lastName, email, phoneNumber, place, number, street, country);
        this.registrationType = registrationType;
        this.registrationRationale = registrationRationale;
    }

    public RegistrationType getRegistrationType() {
        return registrationType;
    }

    public String getRegistrationRationale() {
        return registrationRationale;
    }
}
