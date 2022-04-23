package com.project.team9.controller;

import com.project.team9.dto.LoginDTO;
import com.project.team9.model.user.Client;
import com.project.team9.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "login")
@CrossOrigin("*")
public class LogInController {
    private final ClientService clientService;

    @Autowired
    public LogInController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/validate")
    public ResponseEntity<Long> getClientIdByLoginDTO(@RequestBody LoginDTO loginDTO){
        System.out.println(loginDTO.getEmail());
        System.out.println(loginDTO.getPassword());
        Client client= clientService.getClientByEmailAndPassword(loginDTO);
        if(client==null){
            return new ResponseEntity<>(Long.parseLong("-1"), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(client.getId(), HttpStatus.OK);
        }
    }

}
