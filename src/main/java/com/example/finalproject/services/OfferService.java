package com.example.finalproject.services;

import com.example.finalproject.exceptions.InvalidDateException;
import com.example.finalproject.models.CarBrand;
import com.example.finalproject.models.CarModel;
import com.example.finalproject.models.Offer;

import java.util.List;

public interface OfferService {

    List<CarBrand> getAllCarBrands();

    List<CarModel> getAllCarModels();

    Offer calculateOffer(Offer offer) throws InvalidDateException;
}
