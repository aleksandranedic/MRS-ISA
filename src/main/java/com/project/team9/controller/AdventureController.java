package com.project.team9.controller;

import com.project.team9.exceptions.AdventureNotFoundException;
import com.project.team9.model.resource.Adventure;
import com.project.team9.service.AdventureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="adventure")
public class AdventureController {

    private final AdventureService service;

    @Autowired
    public AdventureController(AdventureService adventureService) {
        this.service = adventureService;
    }

    @GetMapping
    public List<Adventure> getAdventures() {
        return service.getAdventures();
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Adventure getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping("/add")
    public void postBody(Adventure adventure) {
        service.addAdventure(adventure);

    }

    @PutMapping("/{id}/edit")
    Adventure editAdventure(@RequestBody Adventure newAdventure, @PathVariable String id) {
        try {
            return service.editAdventure(newAdventure, id);
        } catch (AdventureNotFoundException e) {
            e.printStackTrace();
        }
        return newAdventure;
    }


    @DeleteMapping("/{id}/delete")
    void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }
}
