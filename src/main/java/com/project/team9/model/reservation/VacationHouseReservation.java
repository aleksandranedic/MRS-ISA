package com.project.team9.model.reservation;

import com.project.team9.model.Tag;
import com.project.team9.model.resource.VacationHouse;
import com.project.team9.model.user.Client;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class VacationHouseReservation extends Reservation{

    @ManyToOne
    private VacationHouse resource;

    public VacationHouseReservation() {
    }

    public VacationHouseReservation(List<Appointment> appointments, int numberOfClients, List<Tag> additionalServices, int price, Client client, VacationHouse vacationHouse) {
        super(appointments, numberOfClients, additionalServices, price, client);
        this.resource = vacationHouse;
    }

    public VacationHouse getResource() {
        return resource;
    }

    public void setResource(VacationHouse vacationHouse) {
        this.resource = vacationHouse;
    }
}
