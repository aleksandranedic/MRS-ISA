package com.project.team9.controller;

import com.project.team9.dto.AdventureDTO;
import com.project.team9.exceptions.AdventureNotFoundException;
import com.project.team9.model.resource.Adventure;
import com.project.team9.service.AdventureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping(value="/dto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AdventureDTO getDTOById(@PathVariable String id) {
        return service.getDTOById(id);
    }

    @PostMapping("/{id}/edit")
    public Adventure edit(@RequestBody AdventureDTO adventure, @PathVariable String id, @RequestParam("fileImage") MultipartFile[] multipartFiles) throws IOException {
        return service.editAdventure(id, adventure, multipartFiles);

    }

    @PostMapping("/add")
    public Long add(AdventureDTO adventure, @RequestParam("fileImage")MultipartFile[] multipartFiles) throws IOException {
        System.out.println("here");
        return service.createAdventure(adventure, multipartFiles);
    }

    @GetMapping("/owner/{ownerId}")
    List<Adventure> findAdventuresWithOwner(@PathVariable String ownerId) {
        return service.findAdventuresWithOwner(ownerId);
    }


    @DeleteMapping("/{id}/delete")
    void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }
}
