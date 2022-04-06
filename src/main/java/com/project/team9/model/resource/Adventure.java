package com.project.team9.model.resource;

import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.reservation.AdventureReservation;
import com.project.team9.model.Address;
import com.project.team9.model.user.vendor.FishingInstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Adventure extends Resource{
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private FishingInstructor owner;
    private int numberOfClients;
    private String fishingEquipment;
    @OneToMany
    private List<AdventureReservation> quickReservations;


    public Adventure() {
    }

    public Adventure(String title, Address address, String description,  String rulesAndRegulations, Pricelist pricelist, int cancellationFee, FishingInstructor owner, int numberOfClients, String fishingEquipment) {
        super(title, address, description, rulesAndRegulations, pricelist, cancellationFee);
        this.owner = owner;
        this.numberOfClients = numberOfClients;
        this.fishingEquipment = fishingEquipment;
        this.quickReservations = new ArrayList<AdventureReservation>();

    }

    public FishingInstructor getOwner() {
        return owner;
    }

    public void setOwner(FishingInstructor owner) {
        this.owner = owner;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public String getFishingEquipment() {
        return fishingEquipment;
    }

    public void setFishingEquipment(String fishingEquipment) {
        this.fishingEquipment = fishingEquipment;
    }
}
