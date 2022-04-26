package com.project.team9.service;

import com.project.team9.model.user.User;
import com.project.team9.model.user.UserAccount;
import com.project.team9.repo.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceSecurity implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final FishingInstructorRepository fishingInstructorRepository;
    private final VacationHouseOwnerRepository vacationHouseOwnerRepository;
    private final AdministratorRepository administratorRepository;
    private final BoatOwnerRepository boatOwnerRepository;
    private final static String USER_NOT_FOUND_MSG ="User with email %s not found";

    public UserServiceSecurity(ClientRepository clientRepository, FishingInstructorRepository fishingInstructorRepository, VacationHouseOwnerRepository vacationHouseOwnerRepository, AdministratorRepository administratorRepository, BoatOwnerRepository boatOwnerRepository) {
        this.clientRepository = clientRepository;
        this.fishingInstructorRepository = fishingInstructorRepository;
        this.vacationHouseOwnerRepository = vacationHouseOwnerRepository;
        this.administratorRepository = administratorRepository;
        this.boatOwnerRepository = boatOwnerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(clientRepository.findByEmail(email).isPresent())
            return clientRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
        else if(fishingInstructorRepository.findByEmail(email).isPresent())
            return clientRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
        else if(vacationHouseOwnerRepository.findByEmail(email).isPresent())
            return clientRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
        else if(administratorRepository.findByEmail(email).isPresent())
            return clientRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
        else if(boatOwnerRepository.findByEmail(email).isPresent())
            return clientRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
        return null;
    }
    public String signUpUser(User user){
        return "";
    }
}
