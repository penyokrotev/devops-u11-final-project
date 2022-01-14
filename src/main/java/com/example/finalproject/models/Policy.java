package com.example.finalproject.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    @NotNull
    private int policyId;

    @OneToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "user_details_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusType status;

    @Column(name = "effective_date_of_the_policy")
    @NotNull
    @Future
    private LocalDate effectiveDateOfThePolicy;

    @Column(name = "image_of_vehicle_registration_certificate")
    @NotNull
    private String imageOfVehicleRegistrationCertificate;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 3, max = 25, message = "First name should be between 3 and 25 characters.")
    private String firstName;

    @Column(name = "middle_name")
    @NotNull
    @Size(min = 3, max = 25, message = "Middle name should be between 3 and 25 characters.")
    private String middleName;

    @Column(name = "last_name")
    @NotNull
    @Size(min = 3, max = 25, message = "Last name should be between 3 and 25 characters.")
    private String lastName;

    @Column(name = "birth_date")
    @NotNull
    @Past
    private LocalDate birthDate;

    @Column(name = "email")
    @NotNull
    @Email(message = "Please enter correct email!")
    private String email;

    @Column(name = "phone")
    @NotNull
    @Size(min = 10, max = 10)
    private String phone;

    @Column(name = "postal_address")
    private String postalAddress;


    @Column(name = "registration_number_of_vehicle")
    @Size(min = 7, max = 8)
    private String registrationNumberOfVehicle;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    public Policy() {

    }

    public Policy(int policyId, Offer offer, User user, StatusType status, LocalDate effectiveDateOfThePolicy,String imageOfVehicleRegistrationCertificate) {
        this.policyId = policyId;
        this.offer = offer;
        this.user = user;
        this.status = status;
        this.effectiveDateOfThePolicy = effectiveDateOfThePolicy;
        this.imageOfVehicleRegistrationCertificate = imageOfVehicleRegistrationCertificate;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public LocalDate getEffectiveDateOfThePolicy() {
        return effectiveDateOfThePolicy;
    }

    public void setEffectiveDateOfThePolicy(LocalDate effectiveDateOfThePolicy) {
        this.effectiveDateOfThePolicy = effectiveDateOfThePolicy;
    }

    public String getImageOfVehicleRegistrationCertificate() {
        return imageOfVehicleRegistrationCertificate;
    }

    public void setImageOfVehicleRegistrationCertificate(String imageOfVehicleRegistrationCertificate) {
        this.imageOfVehicleRegistrationCertificate = imageOfVehicleRegistrationCertificate;
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

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }
}
