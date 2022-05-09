package com.project.team9.service;

import com.project.team9.model.Address;
import com.project.team9.model.user.Client;
import com.project.team9.model.user.vendor.BoatOwner;
import com.project.team9.model.user.vendor.FishingInstructor;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.repo.*;
import com.project.team9.security.token.ConfirmationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceSecurity implements UserDetailsService {

    private final ClientService clientService;
    private final FishingInstructorService fishingInstructorService;
    private final VacationHouseOwnerService vacationHouseOwnerService;
    private final BoatOwnerService boatOwnerService;
    private final AddressService addressService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

    public UserServiceSecurity(ClientService clientService, FishingInstructorService fishingInstructorService, VacationHouseOwnerService vacationHouseOwnerService, BoatOwnerService boatOwnerService, AddressService addressService, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.clientService = clientService;
        this.fishingInstructorService = fishingInstructorService;
        this.vacationHouseOwnerService = vacationHouseOwnerService;
        this.boatOwnerService = boatOwnerService;
        this.addressService = addressService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (clientService.getClientByEmail(username) != null) {
            return clientService.getClientByEmail(username);
        } else if (fishingInstructorService.getFishingInstructorByEmail(username) != null) {
            return fishingInstructorService.getFishingInstructorByEmail(username);
        } else if (vacationHouseOwnerService.getVacationHouseOwnerByEmail(username) != null) {
            return vacationHouseOwnerService.getVacationHouseOwnerByEmail(username);
        } else if (boatOwnerService.getBoatOwnerByEmail(username) != null) {
            return boatOwnerService.getBoatOwnerByEmail(username);
        } else {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
    }

    public String signUpUser(Client user) {
        if (clientService.getClientByEmail(user.getUsername()) != null)
            return "korisnik vec postoji";
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Address address = addressService.getByAttributes(user.getAddress());
        if (address == null) {
            addressService.addAddress(user.getAddress());
        } else {
            user.setAddress(address);
        }
        clientService.addClient(user);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }
    public void addFishingInstructor(FishingInstructor fishingInstructor){
        fishingInstructorService.addFishingInstructor(fishingInstructor);
    }

    public void addBoatOwner(BoatOwner boatOwner){
        boatOwnerService.save(boatOwner);
    }

    public void addVacationHouseOwner(VacationHouseOwner vacationHouseOwner){
        vacationHouseOwnerService.addOwner(vacationHouseOwner);
    }

    public void addClient(Client client){
        clientService.addClient(client);
    }
}
