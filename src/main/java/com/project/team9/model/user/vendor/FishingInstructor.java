package com.project.team9.model.user.vendor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.team9.model.Address;
import com.project.team9.model.resource.Adventure;
import com.project.team9.model.user.Role;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class FishingInstructor extends Vendor {
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Adventure> adventures;
    private String biography;

    public FishingInstructor() {
        this.adventures = new ArrayList<>();
    }

    public FishingInstructor(String password, String firstName, String lastName, String email, String phoneNumber, Address address, Boolean deleted, String registrationRationale, String biography, List<Role> roles) {
        super(password, firstName, lastName, email, phoneNumber, address, deleted, registrationRationale, roles);
        this.adventures = new ArrayList<>();
        this.biography = biography;
    }

    public FishingInstructor(String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country, Boolean deleted, String registrationRationale, String biography, List<Role> roles) {
        super(password, firstName, lastName, email, phoneNumber, place, number, street, country, deleted, registrationRationale, roles);
        this.adventures = new ArrayList<>();
        this.biography = biography;
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
