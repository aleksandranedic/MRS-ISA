package com.project.team9.model.user.vendor;

import com.project.team9.model.Address;
import com.project.team9.model.user.User;
import com.project.team9.model.user.UserRole;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Vendor extends User {
    private String registrationRationale;

    public Vendor() {
    }

    public Vendor(String password, String firstName, String lastName, String email, String phoneNumber, Address address, UserRole userRole,  Boolean deleted, String registrationRationale) {
        super(password, firstName, lastName, email, phoneNumber, address, userRole, deleted);
        this.registrationRationale = registrationRationale;
    }

    public Vendor(String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country, UserRole userRole,  Boolean deleted, String registrationRationale) {
        super(password, firstName, lastName, email, phoneNumber, place, number, street, country, userRole, deleted);
        this.registrationRationale = registrationRationale;
    }

    public String getRegistrationRationale() {
        return registrationRationale;
    }

    public void setRegistrationRationale(String registrationRationale) {
        this.registrationRationale = registrationRationale;
    }
}
