package com.project.team9.model.user;

import com.project.team9.model.Address;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@MappedSuperclass
public abstract class User implements UserDetails{
    @Id
    @SequenceGenerator(
            name="user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE,
            generator="user_sequence"
    )
    private Long id;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    @OneToOne
    private Address address;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Boolean locked;
    private Boolean enabled;
    private Boolean deleted;
    public User() {

    }

    public User(String password, String firstName, String lastName, String email, String phoneNumber, Address address, UserRole userRole, Boolean deleted) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.userRole = userRole;
        this.deleted = deleted;
    }

    public User(String password, String firstName, String lastName, String email, String phoneNumber, String place, String number, String street, String country, UserRole userRole, Boolean deleted) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
        this.deleted = deleted;
        this.address = new Address(place, number, street, country);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
