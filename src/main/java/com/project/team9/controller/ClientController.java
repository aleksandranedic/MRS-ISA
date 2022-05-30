package com.project.team9.controller;

import com.project.team9.dto.UserDTO;
import com.project.team9.model.Address;
import com.project.team9.model.request.DeleteRequest;
import com.project.team9.model.user.Client;
import com.project.team9.service.AddressService;
import com.project.team9.service.ClientService;
import com.project.team9.service.DeleteRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "client")
@CrossOrigin("*")
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

    @PostMapping(value = "changeProfilePicture/{id}")
    public Boolean changeProfilePicture(@PathVariable String id, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        return clientService.changeProfilePicture(id, multipartFile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable String id) {

        return ResponseEntity.ok(clientService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        return ResponseEntity.ok().body(clientService.getClients());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable String id, @RequestBody UserDTO userDTO) {
        Client currentClient = clientService.getById(id);
        currentClient.setFirstName(userDTO.getFirstName());
        currentClient.setLastName(userDTO.getLastName());
        currentClient.setPhoneNumber(userDTO.getPhoneNumber());
        Address address = addressService.getByAttributes(userDTO.getAddress());
        if (address == null) {
            address = new Address();
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
    public ResponseEntity<DeleteRequest> deleteClient(@PathVariable String id, @RequestParam String deletingReason) {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setUserDeletionIdentification(id);
        deleteRequest.setUserType("CLIENT");
        deleteRequest.setComment(deletingReason);
        deleteRequest.setResponse("");
        deleteRequestService.addDeleteRequest(deleteRequest);
        return ResponseEntity.ok().body(deleteRequest);
    }
}

