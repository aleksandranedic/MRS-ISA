package com.project.team9.service;

import com.project.team9.model.Address;
import com.project.team9.model.reservation.Appointment;
import com.project.team9.model.resource.Boat;
import com.project.team9.model.user.Role;
import com.project.team9.model.user.vendor.BoatOwner;
import com.project.team9.repo.BoatOwnerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

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

    private static BoatOwner boatOwner;

    @BeforeAll
    public static void setUp() {

        Address address = new Address("place1", "number1", "street1", "country1");

        boatOwner = new BoatOwner(
                null,
                "password",
                "FirstName",
                "LastName",
                "email",
                "phoneNumber",
                address,
                false,
                "",
                new ArrayList<Boat>(),
                new Role("BOAT_OWNER")
                );
        boatOwner.setId(28L);
    }




    @Test
    void getOwner() { // Student 2
        when(repository.getById(28L)).thenReturn(boatOwner);

        BoatOwner test = boatOwnerService.getOwner(28L);

        assertEquals(test.getEmail(), boatOwner.getEmail());
        assertEquals(test.getDeleted(), boatOwner.getDeleted());

        verify(repository, times(1)).getById(28L);
        verify(repository, times(0)).getById(5L);

    }

    @Test
    void getBoatOwners() { // Student 2
        when(repository.findAll()).thenReturn(Arrays.asList(boatOwner, boatOwner));

        List<BoatOwner> test = boatOwnerService.getBoatOwners();

        assertEquals(2, test.size());

        verify(repository, times(1)).findAll();
    }
}