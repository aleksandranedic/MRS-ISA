package com.project.team9.controller;

import com.project.team9.dto.UserDTO;
import com.project.team9.model.Address;
import com.project.team9.model.request.DeleteRequest;
import com.project.team9.model.user.Client;
import com.project.team9.security.PasswordEncoder;
import com.project.team9.service.AddressService;
import com.project.team9.service.ClientService;
import com.project.team9.service.DeleteRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "client")
@CrossOrigin("*")
public class ClientController {

    private final ClientService clientService;
    private final AddressService addressService;
    private final DeleteRequestService deleteRequestService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientController(ClientService clientService, AddressService addressService, DeleteRequestService deleteRequestService, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.addressService = addressService;
        this.deleteRequestService = deleteRequestService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        return ResponseEntity.ok().body(clientService.getClients());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable String id, @RequestBody UserDTO userDTO) {
        System.out.println("Dosli smo do funkcuje");
        Client currentClient = clientService.getById(id);
        currentClient.setFirstName(userDTO.getFirstName());
        currentClient.setLastName(userDTO.getLastName());
        currentClient.setPhoneNumber(userDTO.getPhoneNumber());
        Address address=addressService.getByAttributes(userDTO.getAddress());
        if(address==null){
            address=new Address();
            address.setStreet(userDTO.getAddress().getStreet());
            address.setNumber(userDTO.getAddress().getNumber());
            address.setCountry(userDTO.getAddress().getCountry());
            address.setPlace(userDTO.getAddress().getPlace());
            addressService.addAddress(address);
        }
        currentClient.setAddress(address);
        currentClient = clientService.addClient(currentClient);
        return ResponseEntity.ok().body(currentClient);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<DeleteRequest> deleteClient(@PathVariable Long id,@RequestParam String deletingReason) {
        DeleteRequest deleteRequest=new DeleteRequest();
        deleteRequest.setUser_id(id);
        deleteRequest.setText(deletingReason);
        deleteRequest.setResponse(""); //TODO sta ovde napisati
        deleteRequestService.addDeleteRequest(deleteRequest);
        return ResponseEntity.ok().body(deleteRequest);
    }
}

