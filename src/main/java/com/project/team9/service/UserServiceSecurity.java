package com.project.team9.service;

import com.project.team9.model.Address;
import com.project.team9.model.user.Client;
import com.project.team9.repo.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceSecurity implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final FishingInstructorRepository fishingInstructorRepository;
    private final VacationHouseOwnerRepository vacationHouseOwnerRepository;
    private final BoatOwnerRepository boatOwnerRepository;
    private final AddressService addressService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

    public UserServiceSecurity(ClientRepository clientRepository, FishingInstructorRepository fishingInstructorRepository, VacationHouseOwnerRepository vacationHouseOwnerRepository, BoatOwnerRepository boatOwnerRepository,AddressService addressService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.clientRepository = clientRepository;
        this.fishingInstructorRepository = fishingInstructorRepository;
        this.vacationHouseOwnerRepository = vacationHouseOwnerRepository;
        this.boatOwnerRepository = boatOwnerRepository;
        this.addressService = addressService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (clientRepository.findByEmail(email).isPresent())
            return clientRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
        else if (fishingInstructorRepository.findByEmail(email).isPresent())
            return clientRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
        else if (vacationHouseOwnerRepository.findByEmail(email).isPresent())
            return clientRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
        else if (boatOwnerRepository.findByEmail(email).isPresent())
            return clientRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
        return null;
    }

    public String signUpUser(Client user, String email) {
        if (user == null)
            return "nije klijent ovo funkcija admina";
        boolean userExists = clientRepository.findByEmail(user.getEmail()).isPresent();
        if (userExists) return "korisnik vec postoji";
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        Address address=addressService.getByAttributes(user.getAddress());
        if(address==null){
            address=addressService.addAddress(user.getAddress());
        }else{
            user.setAddress(address);
        }
        clientRepository.save(user);

        // TODO: send confirmation token

        return "it works";
    }
}
