package com.project.team9.model.reservation;

import com.project.team9.model.Tag;
import com.project.team9.model.resource.Adventure;
import com.project.team9.model.user.Client;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class AdventureReservation extends Reservation{
    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    private Adventure resource;

    public AdventureReservation() {
    }

    public AdventureReservation(List<Appointment> appointments,
                                int numberOfClients,
                                List<Tag> additionalServices,
                                int price,
                                Client client,
                                Adventure resource) {
        super(appointments, numberOfClients, additionalServices, price, client);
        this.resource = resource;
    }

    public Adventure getResource() {
        return resource;
    }

    public void setResource(Adventure adventure) {
        this.resource = adventure;
    }
}
