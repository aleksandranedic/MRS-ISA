package com.project.team9.service;

import com.project.team9.model.user.Client;
import com.project.team9.repo.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository repository;

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public List<Client> getClients() {
        return repository.findAll();
    }

    public Client getById(String id) {
        return repository.getById(Long.parseLong(id));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Client save(Client client) {
        return repository.save(client);
    }
}
