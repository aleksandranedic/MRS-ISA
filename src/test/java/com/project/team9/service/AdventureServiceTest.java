package com.project.team9.service;

import com.project.team9.dto.*;
import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.Tag;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.reservation.AdventureReservation;
import com.project.team9.model.reservation.Appointment;
import com.project.team9.model.resource.Adventure;
import com.project.team9.model.user.Role;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.repo.AdventureRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AdventureServiceTest {

    @Mock
    private AdventureRepository repository;
    @Mock
    private FishingInstructorService fishingInstructorService;
    @Mock
    private TagService tagService;
    @Mock
    private AddressService addressService;
    @Mock
    private PricelistService pricelistService;
    @Mock
    private ImageService imageService;
    @Mock
    private AppointmentService appointmentService;
    @Mock
    private ClientService clientService;
    @Mock
    private AdventureReservationService adventureReservationService;
    @Mock
    private ReservationService reservationService;
    @Mock
    private ClientReviewService clientReviewService;
    @Mock
    private EmailService emailService;
    @Mock
    private UserCategoryService userCategoryService;
    @Mock
    private PointlistService pointlistService;
    @Mock
    private ConfirmationTokenService confirmationTokenService;

    private static Address address;
    private static Adventure adventure1;
    private static Adventure adventure2;
    private static AdventureReservation adventureReservation1;
    private static AdventureReservation adventureReservation2;

    @InjectMocks
    private AdventureService adventureService;

    @BeforeAll
    static void setUp() {
        ArrayList<Appointment> appointments1 = new ArrayList<Appointment>();
        appointments1.add(Appointment.getHourAppointment(2022, 10,1, 10, 0 ));
        appointments1.add(Appointment.getHourAppointment(2022, 10,1, 11, 0 ));
        appointments1.add(Appointment.getHourAppointment(2022, 10,1, 12, 0 ));

        ArrayList<Appointment> appointments2 = new ArrayList<Appointment>();
        appointments2.add(Appointment.getHourAppointment(2022, 10,1, 15, 0 ));
        appointments2.add(Appointment.getHourAppointment(2022, 10,1, 16, 0 ));
        appointments2.add(Appointment.getHourAppointment(2022, 10,1, 17, 0 ));

        address = new Address("place1", "number1", "street1", "country1");

        FishingInstructor fishingInstructor = new FishingInstructor(
                null,
                "password",
                "FirstName",
                "LastName",
                "email",
                "phoneNumber",
                address,
                false,
                "",
                "",
                new Role("FISHING_INSTRUCTOR"),
                new ArrayList<Adventure>()
        );
        fishingInstructor.setId(1L);

        adventure1 = new Adventure(
                "adventure1",
                address,
                "description1",
                "rules1",
                new Pricelist(100, new Date()),
                5,
                fishingInstructor,
                10
        );
        adventure1.setId(1L);
        adventure1.addImage(new Image(null));

        adventure2 = new Adventure(
                "adventure2",
                address,
                "description2",
                "rules2",
                new Pricelist(50, new Date()),
                0,
                fishingInstructor,
                5
        );
        adventure2.setId(2L);
        adventure2.addImage(new Image(null));


        adventureReservation1 = new AdventureReservation(
                appointments1,
                adventure1.getNumberOfClients(),
                new ArrayList<Tag>(),
                150,
                null,
                adventure1,
                false,
                false
        );

        adventure1.addQuickReservation(adventureReservation1);

        adventureReservation2 = new AdventureReservation(
                appointments2,
                adventure2.getNumberOfClients(),
                new ArrayList<Tag>(),
                appointments2.size()*adventure2.getPricelist().getPrice(),
                null,
                adventure2,
                false,
                false
        );

        adventure2.addQuickReservation(adventureReservation2);

    }

    @Test
    void createDTOFromReservation() { //Student 3
        //Akcija
        ReservationDTO dto = adventureService.createDTOFromReservation(adventureReservation1);

        //Provera
        assertFalse(dto.isBusyPeriod());
        assertFalse(dto.isQuickReservation());
        assertEquals(dto.getResourceTitle(), "adventure1");
        assertEquals(dto.getNumberOfClients(), 10);
    }

    @Test
    void convertAdventureToEntityDTO() {  // Student 1
        //Definisanje
        EntityDTO expected = new EntityDTO(
                "adventure1",
                "adventure",
                new Image(null),
                (double) 0,
                1L,
                address,
                100
        );

        //Akcija
        EntityDTO test = adventureService.convertAdventureToEntityDTO(adventure1, 0);

        //Provera
        assertEquals(test.getEntityType(), expected.getEntityType());
        assertEquals(test.getTitle(), expected.getTitle());
        assertEquals(test.getPrice(), expected.getPrice());
        assertEquals(test.getImage().getPath(), expected.getImage().getPath());
        assertEquals(test.getAddress().getCountry(), expected.getAddress().getCountry());
        assertEquals(test.getAddress().getPlace(), expected.getAddress().getPlace());
        assertEquals(test.getAddress().getStreet(), expected.getAddress().getStreet());
        assertEquals(test.getAddress().getNumber(), expected.getAddress().getNumber());
    }

    @Test
    void filterAdventures() {  // Student 1

        DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("H:mm");
        LocalDateTime start = LocalDateTime.of(2022, Month.OCTOBER, 1, 10, 0, 0);
        LocalDateTime end = LocalDateTime.of(2022, Month.OCTOBER, 1, 12, 0, 0);
        String startDate = dateformat.format(start);
        String endDate = dateformat.format(end);
        String startTime = timeformat.format(start);
        String endTime = timeformat.format(end);
        ArrayList<Integer> priceRange = new ArrayList<>();
        priceRange.add(20);
        priceRange.add(120);
        AdventureFilterDTO dto = new AdventureFilterDTO(true, "5", "FirstName LastName", priceRange,"0",  startDate, endDate, startTime, endTime, address.getFullAddressName(), true);

        //Akcija
        List<Adventure> adventures = adventureService.filterAdventures(dto, Arrays.asList(adventure1, adventure2), Arrays.asList(adventureReservation1, adventureReservation2));

        //Provera
        assertEquals(1, adventures.size());
        assertEquals(2, adventures.get(0).getId());
    }


    @Test
    void createAdventureReservationDTO() {  //Student 3
        AdventureQuickReservationDTO dto = adventureService.createAdventureReservationDTO(100, adventureReservation1);

        assertEquals(50, dto.getDiscount());
        assertEquals(3, dto.getDuration());
    }

    @Test
    public void getAdventures() {  // Student 3
        List<Adventure> adventures = Arrays.asList(adventure1, adventure2);

        when(repository.findAll()).thenReturn(adventures);

        List<Adventure> result = adventureService.getAdventures();
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();

    }

    @Test
    public void getById() { //Student 3
        when(repository.getById(1L)).thenReturn(adventure1);

        Adventure result = adventureService.getById("1");

        assertEquals(adventure1.getTitle(), result.getTitle());

        verify(repository, times(1)).getById(1L);
    }
}