package com.project.team9.service;

import com.project.team9.model.reservation.Appointment;
import com.project.team9.repo.BoatOwnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class BoatOwnerServiceTest {
    @Mock
    private BoatOwnerRepository repository;
    @Mock
    private AddressService addressService;
    @Mock
    private ImageService imageService;
    @Mock
    private BoatService boatService;
    @Mock
    private ClientReviewService clientReviewService;
    @Mock
    private UserCategoryService userCategoryService;
    @InjectMocks
    BoatOwnerService boatOwnerService;

    @Test
    void getDates() {
        List<LocalDateTime> dates = boatOwnerService.getDates("01.10.2022.", "05.10.2022.");
        assertEquals(5, dates.size());
    }

    @Test
    void reservationInRangeAttendance() {
        List<Appointment> appointments = Arrays.asList(
                Appointment.getHourAppointment(2022, 10, 10, 5, 15),
                Appointment.getHourAppointment(2022, 10, 11, 6, 15),
                Appointment.getHourAppointment(2022, 10, 12, 7, 15),
                Appointment.getHourAppointment(2022, 10, 13, 8, 15),
                Appointment.getHourAppointment(2022, 10, 14, 9, 15)
        );

        boolean inRange = boatOwnerService.ReservationInRangeAttendance(
                appointments,
                LocalDateTime.of(2022, Month.of(10), 9, 0, 0, 0),
                LocalDateTime.of(2022, Month.of(10), 15, 0, 0, 0));

        assertTrue(inRange);
    }
}