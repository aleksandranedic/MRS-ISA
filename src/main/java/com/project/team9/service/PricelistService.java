package com.project.team9.service;

import com.project.team9.model.buissness.Pricelist;
import com.project.team9.repo.PricelistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricelistService {
    private final PricelistRepository pricelistRepository;

    @Autowired
    public PricelistService(PricelistRepository pricelistRepository) {
        this.pricelistRepository = pricelistRepository;
    }

    public List<Pricelist> getPricelists() {
        return pricelistRepository.findAll();
    }

    public Pricelist getById(String id) {
        return pricelistRepository.getById(Long.parseLong(id));
    }

    public void deleteById(Long id) {
        pricelistRepository.deleteById(id);
    }

    public Pricelist save(Pricelist pricelist) {
        return pricelistRepository.save(pricelist);
    }
}

