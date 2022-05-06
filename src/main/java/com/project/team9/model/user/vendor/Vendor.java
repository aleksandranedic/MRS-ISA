package com.project.team9.model.user.vendor;

import com.project.team9.model.Address;
import com.project.team9.model.user.Role;
import com.project.team9.model.user.User;

import javax.persistence.MappedSuperclass;
import java.util.List;

@MappedSuperclass
public class Vendor extends User {
    private String registrationRationale;

    public Vendor() {
    }

    public Vendor(String password, String firstName, String lastName, String email, String phoneNumber, Address address, Boolean deleted, String registrationRationale, List<Role> roles) {
        super(password, firstName, lastName, email, phoneNumber, address,  deleted, roles);
        this.registrationRationale = registrationRationale;
    }

    public Vendor(String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country, Boolean deleted, String registrationRationale, List<Role> roles) {
        super(password, firstName, lastName, email, phoneNumber, place, number, street, country, deleted, roles);
        this.registrationRationale = registrationRationale;
    }

    public String getRegistrationRationale() {
        return registrationRationale;
    }

    public void setRegistrationRationale(String registrationRationale) {
        this.registrationRationale = registrationRationale;
    }
}
