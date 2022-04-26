package com.project.team9.model.user.vendor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.team9.model.resource.Adventure;
import com.project.team9.model.Address;
import com.project.team9.model.user.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class FishingInstructor extends Vendor {
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Adventure> adventures;
    private String biography;

    public FishingInstructor() {
        this.adventures = new ArrayList<>();
    }

    public FishingInstructor(String password, String firstName, String lastName, String email, String phoneNumber, Address address, UserRole userRole, Boolean deleted, String registrationRationale,  String biography) {
        super(password, firstName, lastName, email, phoneNumber, address, userRole, deleted, registrationRationale);
        this.adventures = new ArrayList<>();
        this.biography = biography;
    }

    public FishingInstructor(String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country, UserRole userRole,  Boolean deleted, String registrationRationale,  String biography) {
        super(password, firstName, lastName, email, phoneNumber, place, number, street, country, userRole, deleted, registrationRationale);
        this.adventures = new ArrayList<>();
        this.biography = biography;
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

    public void addAdventure(Adventure adventure) {
        this.adventures.add(adventure);
    }

    public void removeAdventure(Adventure adventure) {
        this.adventures.remove(adventure);
    }

    public Adventure getAdventure(int id) {
        for (Adventure a : adventures) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }
}
