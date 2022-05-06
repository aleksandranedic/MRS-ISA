package com.project.team9.model.user.vendor;

import com.project.team9.model.resource.Boat;
import com.project.team9.model.Address;
import com.project.team9.model.user.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BoatOwner extends Vendor {
    @OneToMany
    List<Boat> boats;

    public BoatOwner() {
        this.boats = new ArrayList<Boat>();
    }

    public BoatOwner(String password, String firstName, String lastName, String email, String phoneNumber, Address address, Boolean deleted, String registrationRationale, List<Boat> boats, List<Role> roles) {
        super(password, firstName, lastName, email, phoneNumber, address, deleted, registrationRationale, roles);
        this.boats = boats;
    }

    public BoatOwner(String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country, Boolean deleted, String registrationRationale, List<Boat> boats, List<Role> roles) {
        super(password, firstName, lastName, email, phoneNumber, place, number, street, country, deleted, registrationRationale, roles);
        this.boats = boats;
    }

    public void addBoat(Boat boat) {
        this.boats.add(boat);
    }

    public void removeBoat(Boat boat) {
        this.boats.remove(boat);
    }

    public Boat getBoat(int id) {
        for (Boat b : boats) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }
}
