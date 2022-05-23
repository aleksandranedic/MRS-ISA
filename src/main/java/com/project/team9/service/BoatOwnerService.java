package com.project.team9.service;

import com.project.team9.dto.UpdateOwnerDTO;
import com.project.team9.model.Address;
import com.project.team9.model.user.User;
import com.project.team9.model.user.vendor.BoatOwner;
import com.project.team9.model.user.vendor.VacationHouseOwner;
import com.project.team9.repo.BoatOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoatOwnerService {

    private final BoatOwnerRepository repository;
    private final AddressService addressService;

    @Autowired
    public BoatOwnerService(BoatOwnerRepository repository, AddressService addressService) {
        this.repository = repository;
        this.addressService = addressService;
    }

    public BoatOwner getOwner(Long id) {
        return repository.getById(id);
    }

    public void addOwner(BoatOwner owner) {
        repository.save(owner);
    }

    public void updateOwner(Long id, UpdateOwnerDTO newOwner) {
        BoatOwner oldOwner = this.getOwner(id);
        updateOwner(oldOwner, newOwner);
    }

    private void updateOwner(BoatOwner oldOwner, UpdateOwnerDTO newOwner) {
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

    public Boolean checkPassword(Long id, String oldPassword) {
        BoatOwner owner = this.getOwner(id);
        return owner.getPassword().equals(oldPassword);
    }

    public void updatePassword(Long id, String newPassword) {
        BoatOwner owner = this.getOwner(id);
        owner.setPassword(newPassword);
        this.addOwner(owner);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public BoatOwner save(BoatOwner owner) {
        return repository.save(owner);
    }

    public List<BoatOwner> getAll() {
        return repository.findAll();
    }

    public BoatOwner getBoatOwnerByEmail(String username) {
        return repository.findByEmail(username);
    }

    public List<BoatOwner> getUnregisteredBoatOwners() {
        List<BoatOwner> users = this.getAll();
        List<BoatOwner> filtered = new ArrayList<>();
        for (BoatOwner user : users) {
            if (!user.isEnabled() && !user.getDeleted()) {
                filtered.add(user);
            }
        }
        return filtered;
    }
}
