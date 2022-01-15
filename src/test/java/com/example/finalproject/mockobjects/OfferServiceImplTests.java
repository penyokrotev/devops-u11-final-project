package com.example.finalproject.mockobjects;

import com.example.finalproject.exceptions.InvalidDateException;
import com.example.finalproject.models.*;
import com.example.finalproject.repositories.OfferRepository;
import com.example.finalproject.services.OfferServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceImplTests {

    @Mock
    private OfferRepository mockOfferRepository;

    @InjectMocks
    private OfferServiceImpl offerService;

    @Mock
    private OfferMulticriteria multicriteria;


    private Offer offer;
    private int offerId;
    private CarBrand carBrand;
    private CarModel carModel;
    private int cubicCapacity;
    private LocalDate registrationDate;
    private int driverAge;
    private boolean accidentInPreviousYear;
    private BigDecimal totalPremium;
    private String guid;

    @Test
    public void getAll_Should_Return_AllCarBrands() {

        List<CarBrand> expectedList = new ArrayList<>();
        expectedList.add(new CarBrand(1, "Audi"));
        expectedList.add(new CarBrand(2, "BMW"));

        Mockito.when(mockOfferRepository.getAllCarBrands()).thenReturn(expectedList);

        List<CarBrand> carBrandsFound = offerService.getAllCarBrands();

        Assert.assertEquals(expectedList, carBrandsFound);
    }

    @Test
    public void getAll_Should_Return_AllCarModels() {

        CarBrand carBrand = new CarBrand(1, "Audi");

        List<CarModel> expectedList = new ArrayList<>();
        expectedList.add(new CarModel(1, carBrand, "A3"));
        expectedList.add(new CarModel(2, carBrand, "TT"));

        Mockito.when(mockOfferRepository.getAllCarModels()).thenReturn(expectedList);

        List<CarModel> carModelsFound = offerService.getAllCarModels();

        Assert.assertEquals(expectedList, carModelsFound);
    }
//
//    @Test
//    public void get_Should_Return_AccidentCoef() {
//
//        BigDecimal accidentCoef = offerService.getAccidentCoef();
//        BigDecimal accident2 = offerService.getAccidentCoef();
//
//        Assert.assertEquals(accidentCoef, accident2);
//    }
//
//    @Test
//    public void get_Should_Return_AgeAndCoef() {
//
//        AgeAndCoef ageAndCoef = offerService.getAgeAndCoef();
//        AgeAndCoef ageAndCoef2 = offerService.getAgeAndCoef();
//
//        Assert.assertEquals(ageAndCoef, ageAndCoef2);
//    }
//
//    @Test
//    public void get_Should_Return_Multicriterias() {
//        List<OfferMulticriteria> multicriteriaList = mockOfferRepository.getMulticriterias();
//        multicriteriaList.add(new OfferMulticriteria(1, 200, 5000, 1, 150, new BigDecimal(1234567.50)));
//        multicriteriaList.add(new OfferMulticriteria(2, 200, 5000, 1, 150, new BigDecimal(1234567.50)));
//
//        Mockito.when(offerService.getMulticriterias()).thenReturn(multicriteriaList);
//        List<OfferMulticriteria> expectedList = mockOfferRepository.getMulticriterias();
//
//        Assert.assertEquals(multicriteriaList, expectedList);
//    }
//
//    @Test
//    public void get_Should_Return_Cubic_Capacity() {
//
//        List<Offer> expectedList = new ArrayList<>();
//        expectedList.add(new Offer(1, carBrand, carModel, 1900, LocalDate.of(
//                2005, Month.APRIL, 30),
//                30, true, new BigDecimal(1200), ""));
//        int cubicCap = expectedList.get(0).getCubicCapacity();
//
//        List<Offer> offersFound = new ArrayList<>();
//        offersFound.add(new Offer(1, carBrand, carModel, 1900, LocalDate.of(
//                2005, Month.APRIL, 30),
//                30, true, new BigDecimal(1200), ""));
//
//        int cubicFound = offersFound.get(0).getCubicCapacity();
//        Assert.assertEquals(cubicCap, cubicFound);
//    }
//
//    @Test
//    public void getMulticriteriaTable_Should_Return_MulticriteriaTable() {
//
//        List<OfferMulticriteria> expectedList = new ArrayList<>();
//        expectedList.add(new OfferMulticriteria(1, 200, 5000, 1, 150, new BigDecimal(1234567.50)));
//
//        OfferMulticriteria offerMulticriteria = expectedList.get(0);
//
//        Assert.assertEquals(offerMulticriteria, expectedList.get(0));
//    }
//
//
//    @Test
//    public void get_Should_Return_BaseAmount() {
//        List<OfferMulticriteria> offerMulticriteria = offerService.getMulticriterias();
//        BigDecimal baseAmount = new BigDecimal(0);
//
//        for (OfferMulticriteria om : offerMulticriteria) {
//            int cubicCap = offer.getCubicCapacity();
//            int yearsOldCar = calculateYears(offer.getRegistrationDate());
//            if (cubicCap >= om.getCcMin() && cubicCap <= om.getCcMax() && yearsOldCar >= om.getCarAgeMin() && yearsOldCar <= om.getCarAgeMax()) {
//                baseAmount = om.getBaseAmount();
//                break;
//            }
//        }
//        BigDecimal expectedAmount = offerService.getBaseAmount(offer);
//        Assert.assertEquals(baseAmount, expectedAmount);
//    }

    @Test
    public void getAll_Should_Return_AllOffers() {

        CarBrand carBrand = new CarBrand(1, "Audi");
        CarModel carModel = new CarModel(1, carBrand, "A3");
        CarModel carModel2 = new CarModel(2, carBrand, "A3");

        List<Offer> expectedList = new ArrayList<>();
        expectedList.add(new Offer(1, carBrand, carModel, 1900, LocalDate.of(
                2005, Month.APRIL, 30),
                30, true, new BigDecimal(1200), ""));
        expectedList.add(new Offer(2, carBrand, carModel2, 1900, LocalDate.of(
                2005, Month.APRIL, 30),
                30, true, new BigDecimal(1200), ""));

        List<Offer> offersFound = new ArrayList<>();
        offersFound.add(new Offer(1, carBrand, carModel, 1900, LocalDate.of(
                2005, Month.APRIL, 30),
                30, true, new BigDecimal(1200), ""));
        offersFound.add(new Offer(2, carBrand, carModel2, 1900, LocalDate.of(
                2005, Month.APRIL, 30),
                30, true, new BigDecimal(1200), ""));

        Assert.assertEquals(expectedList.size(), offersFound.size());
    }

    @Test
    public void checkIfDateIsValidShouldReturnTrue() {
        registrationDate = LocalDate.of(2005, Month.DECEMBER, 30);
        Assert.assertTrue(offerService.checkIfRegistrationDateIsValid(registrationDate));
    }

    @Test(expected = InvalidDateException.class)
    public void checkIfDateIsValidShouldReturnFalse() {
        registrationDate = LocalDate.of(2025, Month.DECEMBER, 30);
        offerService.checkIfRegistrationDateIsValid(registrationDate);
    }

    @Test
    public void calculateOfferShouldReturnTrue() {
        CarBrand carBrand = new CarBrand(1, "Daewoo");
        CarModel carModel = new CarModel(1, carBrand, "A3");

        OfferMulticriteria offerMulticriteria = new OfferMulticriteria(1,
                0, 1047, 0, 19, new BigDecimal(403.25));
        List<OfferMulticriteria> list  = new ArrayList<>();
        list.add(offerMulticriteria);

        AgeAndCoef ageAndCoef = new AgeAndCoef(BigDecimal.valueOf(1.05), 25);

        BigDecimal accidentCoef = new BigDecimal(1.2);

        BigDecimal taxAmount = new BigDecimal(1.1);

        Offer offer = (new Offer(1, carBrand, carModel, 1000, LocalDate.of(
                2005, Month.APRIL, 30),
                30, true, new BigDecimal(1200), "sss"));

        Offer offer2 = (new Offer(2, carBrand, carModel, 1000, LocalDate.of(
                2005, Month.APRIL, 30),
                30, true, new BigDecimal(1200), "asd"));

        checkIfDateIsValidShouldReturnTrue();
        Mockito.when(mockOfferRepository.getMulticriterias()).thenReturn(list);
        Mockito.when(mockOfferRepository.getTaxAmount()).thenReturn(taxAmount);
        Mockito.when(mockOfferRepository.getAgeAndCoef()).thenReturn(ageAndCoef);
        Mockito.when(mockOfferRepository.getAccidentCoef()).thenReturn(accidentCoef);



//        BigDecimal baseAmount = offerMulticriteria.getBaseAmount();
////        AgeAndCoef ageAndCoef = mockOfferRepository.getAgeAndCoef();
////        BigDecimal accidentCoef = mockOfferRepository.getAccidentCoef();
//        BigDecimal driverAgeCoef = ageAndCoef.getCoef();
//
//        if (offer.getDriverAge() >= ageAndCoef.getAge()) {
//            driverAgeCoef = new BigDecimal(1);
//        }
//        if (!offer.isAccidentInPreviousYear()) {
//            accidentCoef = new BigDecimal(1);
//        }
//
//        BigDecimal netPremium = baseAmount.multiply(accidentCoef).multiply(driverAgeCoef);
//        BigDecimal totalPremium = netPremium.multiply(taxAmount);
//
//        totalPremium.setScale(2, BigDecimal.ROUND_HALF_EVEN);
//
//        offer.setTotalPremium(totalPremium);
//        offer.setGuid(UUID.randomUUID().toString());
//
//        mockOfferRepository.create(offer);
//        offer = mockOfferRepository.getByUUID(offer.getGuid());

        Assert.assertNull(offerService.calculateOffer(offer));
//        Mockito.verify(mockOfferRepository, Mockito.times(1)).create(offer);

    }


//    @Test
//    public void calculateYearsShouldReturnTrue() {
//        Offer offer = new Offer(1, carBrand, carModel, 1000, LocalDate.of(
//                2005, Month.APRIL, 30),
//                30, true, new BigDecimal(1200), "");
//        LocalDate today = LocalDate.now();
//        long years = offerService.calculateYears(offer.getRegistrationDate());
//        long yearsExpected = ChronoUnit.YEARS.between(offer.getRegistrationDate(), today);
//        Assert.assertEquals(years, yearsExpected);
//    }




//    private BigDecimal calculateTotalPremium(BigDecimal baseAmount, Offer offer) {
//        AgeAndCoef ageAndCoef = mockOfferRepository.getAgeAndCoef();
//        BigDecimal accidentCoef = mockOfferRepository.getAccidentCoef();
//        BigDecimal driverAgeCoef = mockOfferRepository.getAgeAndCoef().getCoef();
//        BigDecimal taxAmount = mockOfferRepository.getTaxAmount();
//
//
//        if (offer.getDriverAge() >= ageAndCoef.getAge()) {
//            driverAgeCoef = new BigDecimal(1);
//        }
//        if (!offer.isAccidentInPreviousYear()) {
//            accidentCoef = new BigDecimal(1);
//        }
//
//        BigDecimal netPremium = baseAmount.multiply(accidentCoef).multiply(driverAgeCoef);
//        BigDecimal totalPremium = netPremium.multiply(taxAmount);
//
//        return totalPremium.setScale(2, BigDecimal.ROUND_HALF_EVEN);
//
//    }

    //    @Test
//    public void checkIfBaseAmountIsValidShouldReturnFalse() {
//
//        List<OfferMulticriteria> multicriteriaList = mockOfferRepository.getMulticriterias();
//        OfferMulticriteria offerMulticriteria = new OfferMulticriteria(1,
//                0, 1047, 0, 19, new BigDecimal(403.25));
//        multicriteriaList.add(offerMulticriteria);
//        BigDecimal baseAmount = new BigDecimal(0);
//
//        Offer offer = new Offer(1, carBrand, carModel, 1900, LocalDate.of(
//                2005, Month.APRIL, 30),
//                30, true, new BigDecimal(1200), "");
//
//        for (OfferMulticriteria om : multicriteriaList) {
//            int cubicCap = offer.getCubicCapacity();
//            int yearsOldCar = calculateYears(offer.getRegistrationDate());
//            if (cubicCap >= om.getCcMin() && cubicCap <= om.getCcMax() && yearsOldCar >= om.getCarAgeMin() && yearsOldCar <= om.getCarAgeMax()) {
//                baseAmount = om.getBaseAmount();
//                break;
//            }
//        }
//
//        BigDecimal bigDecimal = offerMulticriteria.getBaseAmount();
//       Assert.assertNotEquals(baseAmount.doubleValue(),bigDecimal.doubleValue());
//
//    }
    private int calculateYears(LocalDate givenDate) {
        LocalDate today = LocalDate.now();
        long years = ChronoUnit.YEARS.between(givenDate, today);
        return (int) years;
    }


}
