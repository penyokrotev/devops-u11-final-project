package com.example.finalproject.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "car_brands")
public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_brand_id")
    @NotNull
    private int brandId;

    @Column(name = "car_brand_name")
    @NotNull
    @Size(min = 3, max = 50, message = "Name of car brand should be between 3 and 50 symbols.")
    private String name;

    public CarBrand() {

    }

    public CarBrand(int brandId, String name) {
        this.brandId = brandId;
        this.name = name;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
