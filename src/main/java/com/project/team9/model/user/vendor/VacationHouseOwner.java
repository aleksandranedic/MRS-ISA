package com.project.team9.model.user.vendor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.team9.model.resource.VacationHouse;
import com.project.team9.model.Address;
import com.project.team9.model.user.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VacationHouseOwner extends Vendor {
    @OneToMany
    List<VacationHouse> vacationHouses;

    public VacationHouseOwner() {
        vacationHouses = new ArrayList<VacationHouse>();
    }

    public VacationHouseOwner(String password, String firstName, String lastName, String email, String phoneNumber, Address address, UserRole userRole, Boolean deleted, String registrationRationale) {
        super(password, firstName, lastName, email, phoneNumber, address, userRole,  deleted, registrationRationale);
        vacationHouses=new ArrayList<>();
    }

    public VacationHouseOwner(String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country, UserRole userRole,  Boolean deleted, String registrationRationale) {
        super(password, firstName, lastName, email, phoneNumber, place, number, street, country, userRole,  deleted, registrationRationale);
        vacationHouses=new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(getUserRole().name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !getLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getEnabled();
    }

    public void addVacationHouse(VacationHouse vacationHouse) {
        this.vacationHouses.add(vacationHouse);
    }

    public void removeVacationHouse(VacationHouse vacationHouse) {
        this.vacationHouses.remove(vacationHouse);
    }

    public VacationHouse getVacationHouse(int id) {
        for (VacationHouse b: vacationHouses) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }


}
