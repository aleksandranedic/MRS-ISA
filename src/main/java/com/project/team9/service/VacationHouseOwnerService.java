package com.project.team9.service;

import com.project.team9.dto.UpdateOwnerDTO;
import com.project.team9.model.Address;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.repo.VacationHouseOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacationHouseOwnerService {

    private final VacationHouseOwnerRepository repository;
    private final AddressService addressService;

    @Autowired
    public VacationHouseOwnerService(VacationHouseOwnerRepository vacationHouseOwnerRepository, AddressService addressService) {
        this.repository = vacationHouseOwnerRepository;
        this.addressService = addressService;
    }

    public VacationHouseOwner getOwner(Long id) {
        return repository.getById(id);
    }

    public void updateOwner(Long id, UpdateOwnerDTO newOwner){
        VacationHouseOwner oldOwner = this.getOwner(id);
        updateOwner(oldOwner, newOwner);
    }
    private void updateOwner(VacationHouseOwner oldOwner, UpdateOwnerDTO newOwner){
        oldOwner.setFirstName(newOwner.getFirstName());
        oldOwner.setLastName(newOwner.getLastName());
        oldOwner.setPhoneNumber(newOwner.getPhoneNumber());
        Address oldAdr = oldOwner.getAddress();
        oldAdr.setStreet(newOwner.getStreet());
        oldAdr.setNumber(newOwner.getNumber());
        oldAdr.setPlace(newOwner.getPlace());
        oldAdr.setCountry(newOwner.getCountry());
        addressService.addAddress(oldAdr);
        this.addOwner(oldOwner);
    }
    public void addOwner(VacationHouseOwner owner) {
        repository.save(owner);
    }

    public Boolean checkPassword(Long id, String oldPassword){
        VacationHouseOwner owner = this.getOwner(id);
        return owner.getPassword().equals(oldPassword);
    }

    public void updatePassword(Long id, String newPassword){
        VacationHouseOwner owner = this.getOwner(id);
        owner.setPassword(newPassword);
        this.addOwner(owner);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public VacationHouseOwner save(VacationHouseOwner owner) {
        return repository.save(owner);
    }

    public VacationHouseOwner getVacationHouseOwnerByEmail(String username) {
        return repository.findByEmail(username);
    }
    public List<VacationHouseOwner> getAll() {
        return repository.findAll();
    }
}