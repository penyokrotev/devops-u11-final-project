package com.example.finalproject.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "base_amount_multicriteria")
public class OfferMulticriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "multicriteria_id")
    @NotNull
    private int multicriteriaId;

    @Column(name = "cc_min")
    @NotNull
    @Min(value = 0)
    private int ccMin;

    @Column(name = "cc_max")
    @NotNull
    @Max(value = 100000)
    private int ccMax;

    @Column(name = "car_age_min")
    @NotNull
    @Min(value = 0)
    private int carAgeMin;

    @Column(name = "car_age_max")
    @NotNull
    @Max(value = 1000)
    private int carAgeMax;

    @Column(name = "base_amount")
    @NotNull
    @Positive
    private BigDecimal baseAmount;

    public OfferMulticriteria() {

    }

    public OfferMulticriteria(int multicriteriaId, int ccMin, int ccMax, int carAgeMin, int carAgeMax, BigDecimal baseAmount) {
        this.multicriteriaId = multicriteriaId;
        this.ccMin = ccMin;
        this.ccMax = ccMax;
        this.carAgeMin = carAgeMin;
        this.carAgeMax = carAgeMax;
        this.baseAmount = baseAmount;
    }

    public int getMulticriteriaId() {
        return multicriteriaId;
    }

    public void setMulticriteriaId(int multicriteriaId) {
        this.multicriteriaId = multicriteriaId;
    }

    public int getCcMin() {
        return ccMin;
    }

    public void setCcMin(int ccMin) {
        this.ccMin = ccMin;
    }

    public int getCcMax() {
        return ccMax;
    }

    public void setCcMax(int ccMax) {
        this.ccMax = ccMax;
    }

    public int getCarAgeMin() {
        return carAgeMin;
    }

    public void setCarAgeMin(int carAgeMin) {
        this.carAgeMin = carAgeMin;
    }

    public int getCarAgeMax() {
        return carAgeMax;
    }

    public void setCarAgeMax(int carAgeMax) {
        this.carAgeMax = carAgeMax;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }
}
