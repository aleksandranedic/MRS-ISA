package com.project.team9.controller;

import com.project.team9.model.Address;
import com.project.team9.model.user.Client;
import com.project.team9.service.AddressService;
import com.project.team9.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "client")
@CrossOrigin("*")
public class ClientController {

    private final ClientService clientService;
    private final AddressService addressService;

    public ClientController(ClientService clientService, AddressService addressService) {
        this.clientService = clientService;
        this.addressService = addressService;
    }

    @Autowired

    @GetMapping
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Client getById(@PathVariable String id) {
        return clientService.getById(id);
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable String id, @RequestBody Client client) {
        Client currentClient = clientService.getById(id);
        System.out.println(currentClient);
        currentClient.setFirstName(client.getFirstName());
        currentClient.setLastName(client.getLastName());
        currentClient.setPhoneNumber(client.getPhoneNumber());
        currentClient.setPassword(client.getPassword());
        Address address=addressService.getByAttributes(client.getAddress());
        if(address==null){
            address=new Address();
            address.setStreet(client.getAddress().getStreet());
            address.setNumber(client.getAddress().getNumber());
            address.setCountry(client.getAddress().getCountry());
            address.setPlace(client.getAddress().getPlace());
            addressService.save(address);
        }
        currentClient.setAddress(address);
        currentClient = clientService.save(currentClient);
        return currentClient;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

