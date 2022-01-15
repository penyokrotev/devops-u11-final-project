package com.example.finalproject.mockobjects;

import com.example.finalproject.components.EmailSender;
import com.example.finalproject.components.FileSaver;
import com.example.finalproject.exceptions.InvalidDateException;
import com.example.finalproject.exceptions.OfferDoesntExistException;
import com.example.finalproject.exceptions.PolicyDoesntExistException;
import com.example.finalproject.models.*;
import com.example.finalproject.repositories.OfferRepository;
import com.example.finalproject.repositories.PolicyRepository;
import com.example.finalproject.services.PolicyServiceImpl;
import com.example.finalproject.services.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PolicyServiceImplTests {

    @Mock
    private PolicyRepository mockPolicyRepository;
    @Mock
    private OfferRepository mockOfferRepository;
    @Mock
    private UserService mockUserService;
    @Mock
    private FileSaver mockFileSaver;
    @Mock
    private EmailSender emailSender;

    private Offer offer;
    private CarBrand carBrand;
    private CarModel carModel;
    private Policy policy;
    private User user;
    private User admin;

    @InjectMocks
    private PolicyServiceImpl policyService;

    @Test
    public void checkIfDriverAgeIsValidShouldReturnTrue() {

        LocalDate date = LocalDate.of(2000, Month.AUGUST, 9);
        int birthDate = 21;

        Assert.assertTrue(policyService.checkIfDriverAgeIsValid(date, birthDate));
    }

    @Test(expected = InvalidDateException.class)
    public void checkIfDriverAgeIsValidShouldThrowException() {

        LocalDate date = LocalDate.of(2005, Month.AUGUST, 15);
        int birthDate = 19;

        policyService.checkIfDriverAgeIsValid(date, birthDate);
    }

    @Test
    public void getAndCheckIfOfferUuidIsValidShouldReturnOfferNotNull() {
        String offerGUID = "CHECK";
        CarBrand carBrand = new CarBrand(1, "Audi");
        CarModel carModel = new CarModel(1, carBrand, "A3");
        Mockito.when(mockOfferRepository.getByUUID("CHECK")).thenReturn(new Offer(2, carBrand, carModel, 1900, LocalDate.of(
                2005, Month.APRIL, 30),
                30, true, new BigDecimal(1200), ""));
        Assert.assertNotNull(policyService.getAndCheckIfOfferUuidIsValid(offerGUID));
    }

    @Test(expected = OfferDoesntExistException.class)
    public void getAndCheckIfOfferUuidIsValidShouldThrowException() {
        String offerGUID = "CHECK";

        Mockito.when(mockOfferRepository.getByUUID("CHECK")).thenReturn(null);

        policyService.getAndCheckIfOfferUuidIsValid(offerGUID);
    }

    @Test
    public void checkIfEffectiveDateIsValidShouldReturnTrue() {
        LocalDate effectiveDate = LocalDate.of(2055, Month.AUGUST, 15);

        Assert.assertTrue(policyService.checkIfEffectiveDateIsValid(effectiveDate));
    }

    @Test(expected = InvalidDateException.class)
    public void checkIfEffectiveDateIsValidShouldReturnException() {
        LocalDate effectiveDate = LocalDate.of(2015, Month.AUGUST, 15);

        policyService.checkIfEffectiveDateIsValid(effectiveDate);
    }

    @Test
    public void getCurrentUserPoliciesShouldReturnListWithPolicies() {
        Policy policy = new Policy();
        Policy policy1 = new Policy();
        List<Policy> policies = new ArrayList<>();
        policies.add(policy);
        policies.add(policy1);
        User user = new User(1, "user@mail.com", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", "");

        Mockito.when(mockUserService.getCurrentUser()).thenReturn(user);
        Mockito.when(mockPolicyRepository.getPoliciesByUserId(1)).thenReturn(policies);

        Assert.assertTrue(policyService.getCurrentUserPolicies(null).containsAll(policies));
    }

    @Test
    public void createPolicyShouldReturnPolicy() {
        MockMultipartFile multipartFile = new MockMultipartFile("file",
                "smth/jpg", "smth/jpg", "test".getBytes());
        PolicyDTO policyDTO = new PolicyDTO("GUID", LocalDate.of(2222, Month.JANUARY, 1),
                "fname", "mname", "lname", LocalDate.of(1991, Month.JANUARY, 1), "email@email.email",
                "1234567890", "1234", "CA555555");
        CarBrand carBrand = new CarBrand(1, "Audi");
        CarModel carModel = new CarModel(1, carBrand, "A3");
        Mockito.when(mockFileSaver.checkIfImageTypeIsValid("jpg")).thenReturn("jpg");
        Mockito.when(mockOfferRepository.getByUUID("GUID")).thenReturn(new Offer(2, carBrand, carModel, 1900, LocalDate.of(
                2005, Month.APRIL, 30),
                31, true, new BigDecimal(1200), ""));
        Mockito.when(mockUserService.getCurrentUser()).thenReturn(new User(1, "user@mail.com", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", ""));
        Mockito.when(mockPolicyRepository.getStatusById(1)).thenReturn(new StatusType());

        Assert.assertNotNull(policyService.createPolicy(policyDTO, multipartFile));
    }

    @Test
    public void getPendingPoliciesShouldReturnListOfPolicies() {
        Policy policy = new Policy();
        Policy policy1 = new Policy();
        List<Policy> policies = new ArrayList<>();
        policies.add(policy);
        policies.add(policy1);

        Mockito.when(mockPolicyRepository.getPendingPolicies()).thenReturn(policies);

        Assert.assertTrue(policyService.getPendingPolicies().containsAll(policies));
    }

    @Test
    public void getAllPolicies() {
        Policy policy = new Policy();
        Policy policy1 = new Policy();
        List<Policy> policies = new ArrayList<>();
        policies.add(policy);
        policies.add(policy1);

        Mockito.when(mockPolicyRepository.getAllPolicies()).thenReturn(policies);

        Assert.assertTrue(policyService.getAllPolicies(null, null).containsAll(policies));
    }
//TODO
//    @Test
//    public void sortByDateTEST() {
//    }
//    @Test
//    public void sortAlphabeticallyTEST() {
//    }

    @Test
    public void getPolicyForUserShouldReturnPolicy() {
        User user = new User(1, "user@mail.com", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", "");
        Offer offer = new Offer();
        StatusType status = new StatusType();
        Policy policy1 = new Policy(1, offer, user, status, LocalDate.of(1994, Month.JANUARY, 1),
                "image1");
        Policy policy2 = new Policy(2, offer, user, status, LocalDate.of(1943, Month.JANUARY, 1),
                "image2");
        List<Policy> policies = new ArrayList<>();
        policies.add(policy1);
        policies.add(policy2);

        Mockito.when(mockUserService.getCurrentUser()).thenReturn(user);
        Mockito.when(mockPolicyRepository.getPoliciesByUserId(1)).thenReturn(policies);

        Policy result = policyService.getPolicyForUser(1);

        Assert.assertTrue(result == policy1);

    }


    @Test(expected = PolicyDoesntExistException.class)
    public void getPolicyForUserShouldThrowException() {
        User user = new User(1, "user@mail.com", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", "");
        Offer offer = new Offer();
        StatusType status = new StatusType();
        Policy policy1 = new Policy(1, offer, user, status, LocalDate.of(1994, Month.JANUARY, 1),
                "image");
        Policy policy2 = new Policy(2, offer, user, status, LocalDate.of(1943, Month.JANUARY, 1),
                "image2");
        List<Policy> policies = new ArrayList<>();
        policies.add(policy1);
        policies.add(policy2);

        Mockito.when(mockUserService.getCurrentUser()).thenReturn(user);
        Mockito.when(mockPolicyRepository.getPoliciesByUserId(1)).thenReturn(policies);
        policyService.getPolicyForUser(3);

    }

    @Test
    public void getPolicyByIdShouldReturnPolicy() {
        StatusType status = new StatusType();
        Policy policy1 = new Policy(1, offer, user, status, LocalDate.of(1994, Month.JANUARY, 1),
                "image");

        Mockito.when(mockPolicyRepository.getPolicyById(1)).thenReturn(policy1);

        Assert.assertTrue(policyService.getPolicyById(1) == policy1);
    }


    @Test(expected = PolicyDoesntExistException.class)
    public void getPolicyByIdShouldThrowException() {

        Mockito.when(mockPolicyRepository.getPolicyById(1)).thenReturn(null);

        policyService.getPolicyById(1);
    }

    @Test
    public void cancelMyPolicyShouldReturnPolicyWithStatusCancelled() {
        StatusType status1 = new StatusType(1, "Pending");
        StatusType status2 = new StatusType(4, "Cancelled");
        Policy policy1 = new Policy(1, offer, user, status1, LocalDate.of(1994, Month.JANUARY, 1),
                "image");
        Policy policy2 = new Policy(2, offer, user, status1, LocalDate.of(1943, Month.JANUARY, 1),
                "image2");
        List<Policy> policies = new ArrayList<>();
        policies.add(policy1);
        policies.add(policy2);
        User user = new User(1, "user@mail.com", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", "");

        Mockito.when(mockUserService.getCurrentUser()).thenReturn(user);
        Mockito.when(mockPolicyRepository.getPolicyById(1)).thenReturn(policy1);
        Mockito.when(mockPolicyRepository.getPendingPoliciesByUserId(1)).thenReturn(policies);
        Mockito.when(mockPolicyRepository.getStatusById(4)).thenReturn(status2);

        Assert.assertTrue(policyService.cancelMyPolicy(1).getStatus() == status2);

    }

    @Test(expected = PolicyDoesntExistException.class)
    public void cancelMyPolicyShouldThrowExceptionPolicyDoesntExist() {
        User user = new User(1, "user@mail.com", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", "");

        Mockito.when(mockUserService.getCurrentUser()).thenReturn(user);
        Mockito.when(mockPolicyRepository.getPolicyById(1)).thenReturn(null);

        policyService.cancelMyPolicy(1);
    }

    @Test
    public void changePolicyStatusToAcceptedAndReturnPolicyWithStatusAccepted() {
        User user = new User(2, "user@mail.com", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", "");
        User admin = new User(1, "user@mail.com", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", "");
        StatusType status1 = new StatusType(1, "Pending");
        StatusType status2 = new StatusType(2, "Accepted");
        Policy policy1 = new Policy(1, offer, user, status1, LocalDate.of(1994, Month.JANUARY, 1),
                "image");
        Mockito.when(mockUserService.getCurrentUser()).thenReturn(admin);
        Mockito.when(mockPolicyRepository.getPolicyById(1)).thenReturn(policy1);
        Mockito.when(mockPolicyRepository.getStatusById(2)).thenReturn(status2);

       Assert.assertTrue(policyService.changePolicyStatus(1, 2).getStatus() == status2);
    }
}
