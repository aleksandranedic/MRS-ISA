package com.project.team9.model.user;

import com.project.team9.model.Address;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Administrator extends User {


    public Administrator() {
    }

    public Administrator(String password, String firstName, String lastName, String email, String phoneNumber, Address address, Boolean deleted, List<Role> roles) {
        super(password, firstName, lastName, email, phoneNumber, address,  deleted, roles);
    }

    public Administrator(String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country, Boolean deleted, List<Role> roles) {
        super(password, firstName, lastName, email, phoneNumber, place, number, street, country,  deleted, roles);
    }


}
