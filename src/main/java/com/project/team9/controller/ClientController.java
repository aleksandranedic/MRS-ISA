package com.project.team9.controller;

import com.project.team9.model.user.Client;
import com.project.team9.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="client")
@CrossOrigin(origins="http://localhost:3000")
public class ClientController {
    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Client> getClients() {
        return service.getClients();
    }
}
