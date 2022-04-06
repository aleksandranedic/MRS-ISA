package com.project.team9.model.resource;

import com.project.team9.model.Image;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.Tag;
import com.project.team9.model.Address;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@MappedSuperclass
public abstract class Resource {
    @Id
    @SequenceGenerator(
            name="resource_sequence",
            sequenceName = "resource_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator="resource_sequence"
    )
    private Long id;
    private String title;
    @OneToOne
    private Address address;
    private String description;
    @OneToMany
    private List<Image> images;

    private String rulesAndRegulations;
    @OneToMany
    private List<Tag> additionalServices;
    @OneToOne
    private Pricelist pricelist;
    private int cancellationFee;

    public Resource() {
    }

    public Resource(String title, Address address, String description,  String rulesAndRegulations, Pricelist pricelist, int cancellationFee) {
        this.title = title;
        this.address = address;
        this.description = description;
        this.images = new ArrayList<Image>();
        this.additionalServices = new ArrayList<Tag>();
        this.rulesAndRegulations = rulesAndRegulations;
        this.pricelist = pricelist;
        this.cancellationFee = cancellationFee;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getRulesAndRegulations() {
        return rulesAndRegulations;
    }

    public void setRulesAndRegulations(String rulesAndRegulations) {
        this.rulesAndRegulations = rulesAndRegulations;
    }

    public List<Tag> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(List<Tag> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public Pricelist getPricelist() {
        return pricelist;
    }

    public void setPricelist(Pricelist pricelist) {
        this.pricelist = pricelist;
    }

    public int getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(int cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
