package com.project.team9.service;

import com.project.team9.dto.NewBusyPeriodDTO;
import com.project.team9.model.Address;
import com.project.team9.model.Tag;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.reservation.Appointment;
import com.project.team9.model.reservation.VacationHouseReservation;
import com.project.team9.model.resource.VacationHouse;
import com.project.team9.repo.VacationHouseRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class VacationHouseServiceTest {
    @Mock
    private VacationHouseRepository repository;
    @Mock
    private AddressService addressService;
    @Mock
    private PricelistService pricelistService;
    @Mock
    private TagService tagService;
    @Mock
    private ImageService imageService;
    @Mock
    private VacationHouseReservationService vacationHouseReservationService;
    @Mock
    private ClientReviewService clientReviewService;
    @Mock
    private AppointmentService appointmentService;
    @Mock
    private ClientService clientService;
    @Mock
    private ReservationService reservationService;
    @Mock
    private EmailService emailService;
    @Mock
    private PointlistService pointlistService;
    @Mock
    private UserCategoryService userCategoryService;
    @InjectMocks
    private VacationHouseService vacationHouseService;


    private static Address address;
    private static VacationHouseReservation reservation;
    private static VacationHouse vacationHouse;

    @BeforeAll
    static void setUp() {


        address = new Address("place1", "number1", "street1", "country1");

        vacationHouse = new VacationHouse(
                "title",
                address,
                "description",
                "rules",
                new Pricelist(100, new Date()),
                0,
                null,
                5,
                5
        );
        vacationHouse.setId(1L);

        reservation = new VacationHouseReservation(
                Arrays.asList(
                        Appointment.getDayAppointment(2022, 10, 1), Appointment.getDayAppointment(2022, 10, 2)),
                        10,
                        new ArrayList<Tag>(),
                        100,
                        null,
                        vacationHouse,
                        false,
                        false
        );
        reservation.setId(1L);
    }

    @Test
    void createBusyPeriod() { // Student 2

        NewBusyPeriodDTO dto = new NewBusyPeriodDTO(
                1L,
                "vacationHouse",
                2022,
                10,
                1,
                2022,
                10,
                2
        );

        when(vacationHouseReservationService.getPossibleCollisionReservations(1L)).thenReturn(new ArrayList<VacationHouseReservation>());

        when(repository.getById(anyLong())).thenReturn(vacationHouse);
        VacationHouseReservation r = vacationHouseService.createBusyPeriod(dto);
        assertTrue(r.isBusyPeriod());
    }

    @Test
    void getVacationHouses() {  // Student 2
        when(repository.findAll()).thenReturn(Arrays.asList(vacationHouse, vacationHouse, vacationHouse));

        List<VacationHouse> test = vacationHouseService.getVacationHouses();

        assertEquals(3, test.size());
        verify(repository, times(1)).findAll();

    }
}