package com.project.team9.model.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.reservation.VacationHouseReservation;
import com.project.team9.model.Address;
import com.project.team9.model.user.vendor.VacationHouseOwner;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VacationHouse extends Resource{
    @ManyToOne
    private VacationHouseOwner owner;
    private int numberOfRooms;
    private int numberOfBedsPerRoom;
    @OneToMany
    private List<VacationHouseReservation> quickReservations;

    public VacationHouse() {
        super();
    }

    public VacationHouse(String title, Address address, String description, String rulesAndRegulations, Pricelist pricelist, int cancellationFee, VacationHouseOwner owner, int numberOfRooms, int numberOfBedsPerRoom) {
        super(title, address, description, rulesAndRegulations, pricelist, cancellationFee);
        this.owner = owner;
        this.numberOfRooms = numberOfRooms;
        this.numberOfBedsPerRoom = numberOfBedsPerRoom;
        this.quickReservations = new ArrayList<VacationHouseReservation>();
    }

    public VacationHouseOwner getOwner() {
        return owner;
    }

    public void setOwner(VacationHouseOwner owner) {
        this.owner = owner;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getNumberOfBedsPerRoom() {
        return numberOfBedsPerRoom;
    }

    public void setNumberOfBedsPerRoom(int numberOfBedsPerRoom) {
        this.numberOfBedsPerRoom = numberOfBedsPerRoom;
    }

    @JsonManagedReference
    public List<VacationHouseReservation> getQuickReservations() {
        return quickReservations;
    }

    public void setQuickReservations(List<VacationHouseReservation> quickReservations) {
        this.quickReservations = quickReservations;
    }
    public void addQuickReservations(VacationHouseReservation quickReservation) {
        this.quickReservations.add(quickReservation);
    }
    @Override
    public String toString() {
        return super.toString() + "VacationHouse{" +
                "owner=" + owner +
                ", numberOfRooms=" + numberOfRooms +
                ", numberOfBedsPerRoom=" + numberOfBedsPerRoom +
                ", quickReservations=" + quickReservations +
                '}';
    }

    public void removeQuickReservation(VacationHouseReservation quickReservation) {
        this.quickReservations.remove(quickReservation);
    }
}
