package com.project.team9.model.reservation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;

@Entity
public class Appointment {
    @Id
    @SequenceGenerator(
            name = "appointment_sequence",
            sequenceName = "appointment_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "appointment_sequence"
    )
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Appointment() {
    }

    public Appointment(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Appointment getVacationHouseAppointment(int year, int month, int day) {
        return new Appointment(LocalDateTime.of(year, month, day, 10, 0, 0), LocalDateTime.of(year, month, day, 10, 0, 0).plusDays(1));
    }

    public static Appointment getAdventureAppointment(int year, int month, int day, int hour, int minute) {
        return new Appointment(LocalDateTime.of(year, month, day, hour, minute,0), LocalDateTime.of(year, month, day, hour+1, minute,0));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
