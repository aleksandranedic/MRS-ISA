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
    private VacationHouse vacationHouse;

    public VacationHouseReservation() {
    }

    public VacationHouseReservation(List<Appointment> appointments, int numberOfClients, List<Tag> additionalServices, int price, Client client, VacationHouse vacationHouse) {
        super(appointments, numberOfClients, additionalServices, price, client);
        this.vacationHouse = vacationHouse;
    }

    public VacationHouse getVacationHouse() {
        return vacationHouse;
    }

    public void setVacationHouse(VacationHouse vacationHouse) {
        this.vacationHouse = vacationHouse;
    }
}
