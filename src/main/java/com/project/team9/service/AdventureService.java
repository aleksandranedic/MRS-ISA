package com.project.team9.service;

import com.project.team9.model.Address;
import com.project.team9.model.Image;
import com.project.team9.model.buissness.Pricelist;
import com.project.team9.model.resource.Adventure;
import com.project.team9.repo.AdventureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdventureService {

    private final AdventureRepository repository;

    @Autowired
    public AdventureService(AdventureRepository adventureRepository) {
        this.repository = adventureRepository;
    }

    public List<Adventure> getAdventures() {
        return repository.findAll();
    }

    public void addAdventure(Adventure adventure) {
        repository.save(adventure);
    }
}
