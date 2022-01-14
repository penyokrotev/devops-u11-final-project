package com.example.finalproject.models;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "user_details")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_details_id")
    private int userId;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    @Size(min = 3, max = 25, message = "First name should be between 3 and 25 characters.")
    private String firstName;

    @Column(name = "middle_name")
    @Size(min = 3, max = 25, message = "Middle name should be between 3 and 25 characters.")
    private String middleName;

    @Column(name = "last_name")
    @Size(min = 3, max = 25, message = "Last name should be between 3 and 25 characters.")
    private String lastName;

    @Column(name = "birth_date")
    @Past(message = "Please, enter your birth date again!")
    private LocalDate birthDate;

    @Column(name = "picture")
    private String picture;

    @Column(name = "phone_number")
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @Column(name = "postal_address")
    private String postalAddress;


    public User() {

    }

    public User(int userId, String email, @Size(min = 3, max = 25, message = "First name should be between 3 and 25 characters.") String firstName, @Size(min = 3, max = 25, message = "Middle name should be between 3 and 25 characters.") String middleName, @Size(min = 3, max = 25, message = "Last name should be between 3 and 25 characters.") String lastName, LocalDate birthDate, String picture, String phoneNumber, String postalAddress) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.postalAddress = postalAddress;
    }

    public int getId() {
        return userId;
    }

    public void setId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

//
//    public Set<Policy> getPolicies() {
//        return policies;
//    }
//
//    public void setPolicies(Set<Policy> policies) {
//        this.policies = policies;
//    }
}
