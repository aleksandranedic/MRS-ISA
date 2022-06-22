package com.project.team9.service;

import com.project.team9.exceptions.ReservationNotAvailableException;
import com.project.team9.model.reservation.Appointment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    private static final ReservationService reservationService = new ReservationService();

    @Test
    public
    void checkAppointmentCollisionHour() {
        Appointment appointment1 = Appointment.getHourAppointment(2022, 10, 10, 5, 15);
        Appointment appointment2 = Appointment.getHourAppointment(2022, 10, 10, 5, 30);
        assertThrows
                (ReservationNotAvailableException.class,
                ()->reservationService.checkAppointmentCollision(appointment1, appointment2));
        ;
    }

    @Test
    public
    void checkAppointmentCollisionHourNoException() {
        Appointment appointment1 = Appointment.getHourAppointment(2022, 10, 10, 5, 15);
        Appointment appointment2 = Appointment.getHourAppointment(2022, 10, 10, 9, 30);
        assertDoesNotThrow(()->reservationService.checkAppointmentCollision(appointment1, appointment2));
        ;
    }

    @Test
    public
    void checkAppointmentCollisionDay() {
        Appointment appointment1 = Appointment.getDayAppointment(2022, 10, 10);
        Appointment appointment2 = Appointment.getDayAppointment(2022, 10, 10);
        assertThrows
                (ReservationNotAvailableException.class,
                        ()->reservationService.checkAppointmentCollision(appointment1, appointment2));
        ;
    }

    @Test
    public
    void checkAppointmentCollisionDayNoException() {
        Appointment appointment1 = Appointment.getDayAppointment(2022, 10, 10);
        Appointment appointment2 = Appointment.getDayAppointment(2022, 10, 15);
        assertDoesNotThrow
                (()->reservationService.checkAppointmentCollision(appointment1, appointment2));
        ;
    }
}