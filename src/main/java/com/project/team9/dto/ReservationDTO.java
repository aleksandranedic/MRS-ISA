package com.project.team9.dto;

import com.project.team9.model.Tag;
import com.project.team9.model.reservation.Appointment;
import com.project.team9.model.user.Client;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationDTO {
    private Long id;
    private Long clientId;
    private Long resourceId;
    private int numberOfClients;
    private List<String> additionalServicesStrings;
    private int price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ReservationDTO() {
    }

    public ReservationDTO(Long id, Long clientId, Long resourceId, int numberOfClients, List<String> additionalServicesStrings, int price, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.clientId = clientId;
        this.resourceId = resourceId;
        this.numberOfClients = numberOfClients;
        this.additionalServicesStrings = additionalServicesStrings;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public List<String> getAdditionalServicesStrings() {
        return additionalServicesStrings;
    }

    public void setAdditionalServicesStrings(List<String> additionalServicesStrings) {
        this.additionalServicesStrings = additionalServicesStrings;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
