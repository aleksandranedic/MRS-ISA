package com.project.team9.service;

import com.project.team9.dto.ClientDTO;
import com.project.team9.model.Address;
import com.project.team9.model.user.Client;
import com.project.team9.repo.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

    @Test
    void updateLoggedUser() {
        Address address = new Address();

        Client client = new Client();
        client.setFirstName("Mika");
        client.setPhoneNumber("060999999");

        when(clientRepository.getById(1L)).thenReturn(client);

        when(addressService.getByAttributes(null)).thenReturn(address);

        ClientDTO dto = new ClientDTO();
        dto.setFirstName("Paja");
        dto.setPhoneNumber("060111111");

        Client result = clientService.updateLoggedUser("1", dto);
        assertEquals("Paja", result.getFirstName());
        assertEquals("060111111", result.getPhoneNumber());
    }

}