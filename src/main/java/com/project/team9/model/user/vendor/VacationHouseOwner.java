package com.project.team9.model.user.vendor;

import com.project.team9.model.resource.VacationHouse;
import com.project.team9.model.Address;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
@Entity
public class VacationHouseOwner extends Vendor {
    @OneToMany
    List<VacationHouse> vacationHouses;

    public VacationHouseOwner() {
        vacationHouses = new ArrayList<VacationHouse>();
    }

    public VacationHouseOwner(String password, String firstName, String lastName, String email, String phoneNumber, Address address, RegistrationType registrationType, String registrationRationale) {
        super(password, firstName, lastName, email, phoneNumber, address, registrationType, registrationRationale);
        vacationHouses = new ArrayList<VacationHouse>();
    }

    public VacationHouseOwner(String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country, RegistrationType registrationType, String registrationRationale) {
        super(password, firstName, lastName, email, phoneNumber, place, number, street, country, registrationType, registrationRationale);
        vacationHouses = new ArrayList<VacationHouse>();
    }

    public void addVacationHouse(VacationHouse vacationHouse) {
        this.vacationHouses.add(vacationHouse);
    }

    public void removeVacationHouse(VacationHouse vacationHouse) {
        this.vacationHouses.remove(vacationHouse);
    }

    public VacationHouse getVacationHouse(int id) {
        for (VacationHouse b: vacationHouses) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }


}
