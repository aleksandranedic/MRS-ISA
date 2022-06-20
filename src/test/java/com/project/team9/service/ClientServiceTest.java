package com.project.team9.service;

import com.project.team9.dto.ClientDTO;
import com.project.team9.model.Address;
import com.project.team9.model.user.Client;
import com.project.team9.model.user.Role;
import com.project.team9.repo.ClientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ImageService imageService;
    @Mock
    private AdventureReservationService adventureReservationService;
    @Mock
    private BoatReservationService boatReservationService;
    @Mock
    private VacationHouseReservationService vacationHouseReservationService;
    @Mock
    private AddressService addressService;
    @InjectMocks
    private ClientService clientService;

    private static Client client;
    private static Address address;

    @BeforeAll
    public static void setUp() {

        address = new Address("place1", "number1", "street1", "country1");
        client = new Client(
                "password",
                "Mika",
                "Mikic",
                "email",
                "060999999",
                "",
                "",
                "",
                "",
                false,
                new Role("CLIENT")
        );
        client.setAddress(address);

    }

    @Test
    @Transactional
    @Rollback(true)
    void updateLoggedUser() {  // Student 1
        when(clientRepository.getById(1L)).thenReturn(client);

        when(addressService.getByAttributes(null)).thenReturn(address);

        ClientDTO dto = new ClientDTO();
        dto.setFirstName("Paja");
        dto.setPhoneNumber("060111111");

        Client result = clientService.updateLoggedUser("1", dto);
        assertEquals("Paja", result.getFirstName());
        assertEquals("060111111", result.getPhoneNumber());
    }

    @Test
    void getClients() { // Student 1

        when(clientRepository.findAll()).thenReturn(Arrays.asList(client, client));
        List<Client> test = clientService.getClients();
        assertEquals(2, test.size());
        verify(clientRepository, times(1)).findAll();

    }

    @Test
    void getById() { //Student 1
        when(clientRepository.getById(1L)).thenReturn(client);
        Client test = clientService.getById("1");
        assertEquals(test.getPassword(), client.getPassword());
        verify(clientRepository, times(1)).getById(1L);

    }

    @Test
    void getClientByEmail() {  // Student 1
        when(clientRepository.findByEmail("email")).thenReturn(client);
        Client test = clientService.getClientByEmail("email");
        assertEquals(test.getPassword(), client.getPassword());
        verify(clientRepository, times(1)).findByEmail("email");
    }
}