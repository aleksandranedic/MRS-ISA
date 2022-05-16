package com.project.team9.service;

import com.project.team9.model.user.Administrator;
import com.project.team9.repo.AdministratorRepository;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {
    private final AdministratorRepository administratorRepository;

    public AdministratorService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }
    public Administrator addAdmin(Administrator administrator){
       return administratorRepository.save(administrator);
    }
}
