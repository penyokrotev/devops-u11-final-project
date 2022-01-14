package com.example.finalproject.repositories;

import com.example.finalproject.models.*;

import java.math.BigDecimal;
import java.util.List;

public interface OfferRepository {

    List<CarBrand> getAllCarBrands();

    List<CarModel> getAllCarModels();

    AgeAndCoef getAgeAndCoef();

    BigDecimal getAccidentCoef();

    BigDecimal getTaxAmount();

    List<OfferMulticriteria> getMulticriterias();

    void updateMulticriteria(OfferMulticriteria offerMulticriteria);

    void create(Offer offer);

    Offer getByUUID(String guid);

    void updateMulticriteriaInformation(MulticriteriaHistory multicriteriaHistory, CurrentMulticriteria currentMulticriteria);

    CurrentMulticriteria getCurrentMulticriteria();
}
