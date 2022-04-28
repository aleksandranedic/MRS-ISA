package com.project.team9.controller;

import com.project.team9.model.Address;
import com.project.team9.model.request.DeleteRequest;
import com.project.team9.model.user.Client;
import com.project.team9.service.AddressService;
import com.project.team9.service.ClientService;
import com.project.team9.service.DeleteRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "client")
public class ClientController {

    private final ClientService clientService;
    private final AddressService addressService;
    private final DeleteRequestService deleteRequestService;

    @Autowired
    public ClientController(ClientService clientService, AddressService addressService, DeleteRequestService deleteRequestService) {
        this.clientService = clientService;
        this.addressService = addressService;
        this.deleteRequestService = deleteRequestService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        return ResponseEntity.ok().body(clientService.getClients());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getById(@PathVariable String id) {
        return "ResponseEntity.ok().body(clientService.getById(id))";
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable String id, @RequestBody Client client) {
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
            addressService.addAddress(address);
        }
        currentClient.setAddress(address);
        currentClient = clientService.addClient(currentClient);
        return ResponseEntity.ok().body(currentClient);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DeleteRequest> deleteClient(@PathVariable Long id) {
        DeleteRequest deleteRequest=new DeleteRequest();
        deleteRequest.setUser_id(id);
        deleteRequest.setText(""); //sta ovde napisati
        deleteRequest.setResponse(""); //sta ovde napisati
        deleteRequestService.addDeleteRequest(deleteRequest);
        return ResponseEntity.ok().body(deleteRequest);
    }
}

