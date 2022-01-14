package com.example.finalproject.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class PolicyDTO {

    @NotNull
    @Size(min = 36, max = 36, message = "Please enter VALID Offer Code!")
    private String offerGUID;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Future
    private LocalDate effectiveDateOTP;

    @NotNull
    @Size(min = 3, max = 25, message = "First name should be between 3 and 25 characters.")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 25, message = "Middle name should be between 3 and 25 characters.")
    private String middleName;

    @NotNull
    @Size(min = 3, max = 25, message = "Last name should be between 3 and 25 characters.")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Past
    private LocalDate birthDate;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 10, max = 10)
    private String phone;

    @NotNull
    private String postalAddress;

    @NotNull
    @Size(min = 6, max = 6)
    private String registrationNumberOfVehicle;

    public PolicyDTO() {

    }

    public PolicyDTO(String offerGUID, LocalDate effectiveDateOTP, String firstName, String middleName, String lastName, LocalDate birthDate, String email, String phone, String postalAddress, String registrationNumberOfVehicle) {
        this.offerGUID = offerGUID;
        this.effectiveDateOTP = effectiveDateOTP;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.postalAddress = postalAddress;
        this.registrationNumberOfVehicle = registrationNumberOfVehicle;
    }

    public String getOfferGUID() {
        return offerGUID;
    }

    public void setOfferGUID(String offerGUID) {
        this.offerGUID = offerGUID;
    }

    public LocalDate getEffectiveDateOTP() {
        return effectiveDateOTP;
    }

    public void setEffectiveDateOTP(LocalDate effectiveDateOTP) {
        this.effectiveDateOTP = effectiveDateOTP;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getRegistrationNumberOfVehicle() {
        return registrationNumberOfVehicle;
    }

    public void setRegistrationNumberOfVehicle(String registrationNumberOfVehicle) {
        this.registrationNumberOfVehicle = registrationNumberOfVehicle;
    }
}
