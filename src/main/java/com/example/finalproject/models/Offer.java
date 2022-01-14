package com.example.finalproject.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    @NotNull
    private int offerId;

    @ManyToOne
    @JoinColumn(name = "car_brand_id")
    private CarBrand carBrand;

    @ManyToOne
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;

    @Column(name = "cubic_capacity")
    @NotNull
    @Min(value = 1)
    @Max(value = 99999)
    private int cubicCapacity;

    @Column(name = "registration_date")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationDate;

    @Column(name = "driver_age")
    @NotNull
    @Min(value = 18, message = "Please enter your correct age!")
    @Max(value = 120, message = "Please enter your correct age!")
    private int driverAge;

    @Column(name = "accident_in_previous_year")
    @NotNull
    private boolean accidentInPreviousYear;

    @Column(name = "total_premium")
    @Positive
    private BigDecimal totalPremium;

    @Column(name = "offer_guid")
    private String guid;

    public Offer() {

    }



    public Offer(int offerId, CarBrand carBrand, CarModel carModel, int cubicCapacity, LocalDate registrationDate, int driverAge, boolean accidentInPreviousYear, BigDecimal totalPremium, String guid) {
        this.offerId = offerId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.cubicCapacity = cubicCapacity;
        this.registrationDate = registrationDate;
        this.driverAge = driverAge;
        this.accidentInPreviousYear = accidentInPreviousYear;
        this.totalPremium = totalPremium;
        this.guid = guid;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public int getCubicCapacity() {
        return cubicCapacity;
    }

    public void setCubicCapacity(int cubicCapacity) {
        this.cubicCapacity = cubicCapacity;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getDriverAge() {
        return driverAge;
    }

    public void setDriverAge(int driverAge) {
        this.driverAge = driverAge;
    }

    public boolean isAccidentInPreviousYear() {
        return accidentInPreviousYear;
    }

    public void setAccidentInPreviousYear(boolean accidentInPreviousYear) {
        this.accidentInPreviousYear = accidentInPreviousYear;
    }

    public BigDecimal getTotalPremium() {
        return totalPremium;
    }

    public void setTotalPremium(BigDecimal totalPremium) {
        this.totalPremium = totalPremium;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
