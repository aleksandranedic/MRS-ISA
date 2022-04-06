package com.project.team9.model.user.vendor;

import com.project.team9.model.resource.Boat;
import com.project.team9.model.Address;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BoatOwner extends Vendor{
   @OneToMany
    List<Boat> boats;

    public BoatOwner() {
        this.boats = new ArrayList<Boat>();
    }

    public BoatOwner(String password, String firstName, String lastName, String email, String phoneNumber, Address address, RegistrationType registrationType, String registrationRationale) {
        super(password, firstName, lastName, email, phoneNumber, address, registrationType, registrationRationale);
        this.boats = new ArrayList<Boat>();
    }

    public BoatOwner(String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country, RegistrationType registrationType, String registrationRationale) {
        super(password, firstName, lastName, email, phoneNumber, place, number, street, country, registrationType, registrationRationale);
        this.boats = new ArrayList<Boat>();
    }

    public void addBoat(Boat boat) {
        this.boats.add(boat);
    }

    public void removeBoat(Boat boat) {
        this.boats.remove(boat);
    }

    public Boat getBoat(int id) {
        for (Boat b: boats) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }
}
