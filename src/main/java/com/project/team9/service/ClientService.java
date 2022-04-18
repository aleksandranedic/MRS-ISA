package com.project.team9.service;

import com.project.team9.model.user.Client;
import com.project.team9.repo.AddressRepository;
import com.project.team9.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client getById(String id) {
        return clientRepository.getById(Long.parseLong(id));
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }
}
