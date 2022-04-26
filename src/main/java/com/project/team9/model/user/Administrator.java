package com.project.team9.model.user;

import com.project.team9.model.Address;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
public class Administrator extends User {


    public Administrator() {
    }

    public Administrator(String password, String firstName, String lastName, String email, String phoneNumber, Address address, UserRole userRole, Boolean deleted) {
        super(password, firstName, lastName, email, phoneNumber, address, userRole, deleted);
    }

    public Administrator(String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country, UserRole userRole, Boolean deleted) {
        super(password, firstName, lastName, email, phoneNumber, place, number, street, country, userRole, deleted);
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
