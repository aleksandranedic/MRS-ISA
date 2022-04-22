package com.project.team9.dto;

import com.project.team9.model.Tag;
import com.project.team9.model.reservation.VacationHouseReservation;

import java.util.List;

public class VacationHouseDTO {
    private Long id;
    private String title;
    private String address;
    private String number;
    private String street;
    private String city;
    private String country;
    private String description;
    private List<String> imagePaths;
    private String rulesAndRegulations;
    private List<Tag> additionalServices;
    private int priceList;
    private int cancellationFee;
    private int numberOfRooms;
    private int capacity;
    private List<VacationHouseReservation> quickReservations;

    public VacationHouseDTO() {}

    public VacationHouseDTO(Long id, String title,String address, String number, String street, String city, String country, String description, List<String> imagePaths, String rulesAndRegulations, List<Tag> additionalServices, int priceList, int cancellationFee, int numberOfRooms, int capacity, List<VacationHouseReservation> quickReservations) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.address = address;
        this.street = street;
        this.city = city;
        this.country = country;
        this.description = description;
        this.imagePaths = imagePaths;
        this.rulesAndRegulations = rulesAndRegulations;
        this.additionalServices = additionalServices;
        this.priceList = priceList;
        this.cancellationFee = cancellationFee;
        this.numberOfRooms = numberOfRooms;
        this.capacity = capacity;
        this.quickReservations = quickReservations;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
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

    public int getPriceList() {
        return priceList;
    }

    public void setPriceList(int priceList) {
        this.priceList = priceList;
    }

    public int getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(int cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<VacationHouseReservation> getQuickReservations() {
        return quickReservations;
    }

    public void setQuickReservations(List<VacationHouseReservation> quickReservations) {
        this.quickReservations = quickReservations;
    }
}
