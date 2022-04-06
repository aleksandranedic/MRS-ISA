package com.project.team9.model.user;

import com.project.team9.model.Address;

import javax.persistence.*;

@Entity
public class Administrator extends User {


    public Administrator() {
    }

    public Administrator(String password, String firstName, String lastName, String email, String phoneNumber, Address address) {
        super(password, firstName, lastName, email, phoneNumber, address);
    }

    public Administrator(String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country) {
        super(password, firstName, lastName, email, phoneNumber, place, number, street, country);
    }
}
