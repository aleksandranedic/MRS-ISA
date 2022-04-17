package com.project.team9.dto;

import com.project.team9.model.Address;
import com.project.team9.model.user.Client;

public class UserDTO extends Client {

    public UserDTO(String password, String firstName, String lastName, String email, String phoneNumber, Address address) {
        super(password, firstName, lastName, email, phoneNumber, address);
    }

    public UserDTO(String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country) {
        super(password, firstName, lastName, email, phoneNumber, place, number, street, country);
    }
}
