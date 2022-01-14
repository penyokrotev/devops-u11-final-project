package com.example.finalproject.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "age_coef")
public class AgeAndCoef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "age_coef_id")
    @NotNull
    private int brandId;

    @Column(name = "age_coef")
    @NotNull
    @Positive(message = "Age Coefficient must be positive!")
    private BigDecimal coef;

    @Column(name = "under_age")
    @NotNull
    @Min(value = 18,message = "Please enter your correct age!")
    @Max(value = 120,message = "Please enter your correct age!")
    private int age;

    public AgeAndCoef() {

    }


    public AgeAndCoef(@NotNull @Positive(message = "Age Coefficient must be positive!") BigDecimal coef, @NotNull @Min(value = 18, message = "Please enter your correct age!") @Max(value = 120, message = "Please enter your correct age!") int age) {
        this.coef = coef;
        this.age = age;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public BigDecimal getCoef() {
        return coef;
    }

    public void setCoef(BigDecimal coef) {
        this.coef = coef;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
