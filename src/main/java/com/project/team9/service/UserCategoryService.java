package com.project.team9.service;

import com.project.team9.model.buissness.UserCategory;
import com.project.team9.repo.UserCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserCategoryService {
    private final ClientService clientService;
    private final UserCategoryRepository repository;

    @Autowired
    public UserCategoryService(ClientService clientService, UserCategoryRepository repository) {
        this.clientService = clientService;
        this.repository = repository;
    }

    public List<UserCategory> getAllClientCategories() {
        return repository.getAllClientCategories();
    }

    public List<UserCategory> getAllVendorCategories() {
        return repository.getAllVendorCategories();
    }

    public Long addUserCategory(UserCategory userCategory) {
        if (Objects.equals(userCategory.getType(), "CLIENT")) {
            userCategory.setClientCategory(true);
            userCategory.setVendorCategory(false);
        }
        else if (Objects.equals(userCategory.getType(), "VENDOR")) {
            userCategory.setClientCategory(false);
            userCategory.setVendorCategory(true);
        }

        return repository.save(userCategory).getId();
    }
}
