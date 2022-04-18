package com.project.team9.service;

import com.project.team9.exceptions.AdventureNotFoundException;
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

    public Adventure getById(String id) {
        return repository.getById(Long.parseLong(id));
    }

    public void deleteById(String id) {
        repository.deleteById(Long.parseLong(id));
    }

    public Adventure editAdventure(Adventure newAdventure, String id) throws AdventureNotFoundException {

        return repository.findById(Long.parseLong(id)).map(adventure -> {
            adventure.setNumberOfClients(newAdventure.getNumberOfClients());
            adventure.setTitle(newAdventure.getTitle());
            adventure.setDescription(newAdventure.getDescription());
            adventure.setPricelist(newAdventure.getPricelist());
            adventure.setCancellationFee(newAdventure.getCancellationFee());
            adventure.setFishingEquipment(newAdventure.getFishingEquipment());
            adventure.setAdditionalServices(newAdventure.getAdditionalServices());
            return repository.save(adventure);
        }).orElseThrow(()->  new AdventureNotFoundException(id));
    }
}
