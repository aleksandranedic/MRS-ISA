package com.project.team9.model.reservation;

import com.project.team9.model.Tag;
import com.project.team9.model.resource.Boat;
import com.project.team9.model.user.Client;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class BoatReservation extends Reservation{
    @ManyToOne
    private Boat boat;

    public BoatReservation() {
    }

    public BoatReservation(List<Appointment> appointments, int numberOfClients, List<Tag> additionalServices, int price, Client client, Boat boat) {
        super(appointments, numberOfClients, additionalServices, price, client);
        this.boat = boat;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }
}
