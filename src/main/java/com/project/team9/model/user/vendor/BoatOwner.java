package com.project.team9.model.user.vendor;

import com.project.team9.model.resource.Boat;
import com.project.team9.model.Address;
import com.project.team9.model.user.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
public class BoatOwner extends Vendor{
   @OneToMany
    List<Boat> boats;

    public BoatOwner() {
        this.boats = new ArrayList<Boat>();
    }

    public BoatOwner(String password, String firstName, String lastName, String email, String phoneNumber, Address address, UserRole userRole, Boolean deleted, String registrationRationale, List<Boat> boats) {
        super(password, firstName, lastName, email, phoneNumber, address, userRole,  deleted, registrationRationale);
        this.boats = boats;
    }

    public BoatOwner(String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country, UserRole userRole,  Boolean deleted, String registrationRationale, List<Boat> boats) {
        super(password, firstName, lastName, email, phoneNumber, place, number, street, country, userRole,  deleted, registrationRationale);
        this.boats = boats;
    }

    public void addBoat(Boat boat) {
        this.boats.add(boat);
    }

    public void removeBoat(Boat boat) {
        this.boats.remove(boat);
    }

    public Boat getBoat(int id) {
        for (Boat b: boats) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
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
}
