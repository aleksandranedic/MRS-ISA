package com.project.team9.controller;

import com.project.team9.model.resource.Adventure;
import com.project.team9.service.AdventureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="adventure")
@CrossOrigin("*")
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
}
