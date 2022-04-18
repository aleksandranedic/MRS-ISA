package com.project.team9.model.user.vendor;

import com.project.team9.model.resource.Adventure;
import com.project.team9.model.Address;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FishingInstructor extends Vendor {
    @OneToMany(cascade= CascadeType.ALL, orphanRemoval = true)
    List<Adventure> adventures;
    String biography;

    public FishingInstructor() {
        this.adventures = new ArrayList<>();
    }

    public FishingInstructor(String password,
                             String firstName,
                             String lastName,
                             String email,
                             String phoneNumber,
                             Address address,
                             RegistrationType registrationType,
                             String registrationRationale,
                             String biography) {
        super(password, firstName, lastName, email, phoneNumber, address, registrationType, registrationRationale);
        this.biography = biography;
        this.adventures = new ArrayList<>();

    }

    public FishingInstructor(String email,
                             String password,
                             String firstName,
                             String lastName,
                             String phoneNumber,
                             String place,
                             String number,
                             String street,
                             String country,
                             RegistrationType registrationType,
                             String registrationRationale,
                             String biography) {
        super(password, firstName, lastName, email, phoneNumber, place, number, street, country, registrationType, registrationRationale);
        this.biography = biography;
        this.adventures = new ArrayList<>();
    }

    public void addAdventure(Adventure adventure) {
        this.adventures.add(adventure);
    }

    public void removeAdventure(Adventure adventure) {
        this.adventures.remove(adventure);
    }

    public Adventure getAdventure(int id) {
        for (Adventure a : adventures) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }
}
