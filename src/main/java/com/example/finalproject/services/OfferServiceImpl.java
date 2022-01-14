package com.example.finalproject.services;

import com.example.finalproject.exceptions.InvalidDateException;
import com.example.finalproject.models.*;
import com.example.finalproject.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class OfferServiceImpl implements OfferService {
    private static final String INVALID_REGISTRATION_DATE_EM = "Registration date is not valid!";
    private OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }


    @Override
    public List<CarBrand> getAllCarBrands() {
        List<CarBrand> carBrands = offerRepository.getAllCarBrands();
        return carBrands;
    }

    @Override
    public List<CarModel> getAllCarModels() {
        List<CarModel> carModels = offerRepository.getAllCarModels();
        return carModels;
    }

    @Override
    public Offer calculateOffer(Offer offer) throws InvalidDateException {
        checkIfRegistrationDateIsValid(offer.getRegistrationDate());

        BigDecimal baseAmount = getBaseAmount(offer);

        BigDecimal totalPremium = calculateTotalPremium(baseAmount, offer);

        offer.setTotalPremium(totalPremium);
        offer.setGuid(UUID.randomUUID().toString());

        offerRepository.create(offer);
        offer = offerRepository.getByUUID(offer.getGuid());
        return offer;
    }

    private BigDecimal calculateTotalPremium(BigDecimal baseAmount, Offer offer) {
        AgeAndCoef ageAndCoef = offerRepository.getAgeAndCoef();
        BigDecimal accidentCoef = offerRepository.getAccidentCoef();
        BigDecimal driverAgeCoef = ageAndCoef.getCoef();
        BigDecimal taxAmount = offerRepository.getTaxAmount();


        if (offer.getDriverAge() >= ageAndCoef.getAge()) {
            driverAgeCoef = new BigDecimal(1);
        }
        if (!offer.isAccidentInPreviousYear()) {
            accidentCoef = new BigDecimal(1);
        }

        BigDecimal netPremium = baseAmount.multiply(accidentCoef).multiply(driverAgeCoef);
        BigDecimal totalPremium = netPremium.multiply(taxAmount);

        return totalPremium.setScale(2, BigDecimal.ROUND_HALF_EVEN);

    }

    private BigDecimal getBaseAmount(Offer offer) {
        List<OfferMulticriteria> multicriteriaList = offerRepository.getMulticriterias();
        BigDecimal baseAmount = new BigDecimal(0);

        for (OfferMulticriteria om : multicriteriaList) {
            int cubicCap = offer.getCubicCapacity();
            int yearsOldCar = calculateYears(offer.getRegistrationDate());
            if (cubicCap >= om.getCcMin() && cubicCap <= om.getCcMax() && yearsOldCar >= om.getCarAgeMin() && yearsOldCar <= om.getCarAgeMax()) {
                baseAmount = om.getBaseAmount();
                break;
            }
        }
        return baseAmount;
    }

    public boolean checkIfRegistrationDateIsValid(LocalDate registraionDate) throws InvalidDateException {
        if (registraionDate.isAfter(LocalDate.now())) {
            throw new InvalidDateException(INVALID_REGISTRATION_DATE_EM);
        }
        else {
            return true;
        }
    }


    private int calculateYears(LocalDate givenDate) {
        LocalDate today = LocalDate.now();
        long years = ChronoUnit.YEARS.between(givenDate, today);
        return (int) years;
    }
}
