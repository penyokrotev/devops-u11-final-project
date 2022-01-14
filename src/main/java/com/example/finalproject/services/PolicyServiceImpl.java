package com.example.finalproject.services;

import com.example.finalproject.components.EmailSender;
import com.example.finalproject.components.FileSaver;
import com.example.finalproject.exceptions.*;
import com.example.finalproject.models.*;
import com.example.finalproject.repositories.OfferRepository;
import com.example.finalproject.repositories.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyServiceImpl implements PolicyService {

    private static final String EFFECTIVE_DATE_OF_THE_POLICY_EM = "Effective date of the policy cant be in the past!";
    private static final String OFFER_GUID_NOT_EXISTS_EM = "The code that you entered doesnt lead to offer, try creating new offer!";
    private static final String POLICY_DOESNT_EXIST_EM = "Policy with id %s doesnt exist";
    private static final String IMAGE_TYPE_EM = "Image should be png/jpg/jpeg";

    private static final String DRIVER_AGE_DOESNT_MATCH = "Driver age from offer doesn't match with Birth date from policy!";
    private static final String YOU_HAVE_NO_SUCH_POLICY = "You have no such policy!";

    private static final String FROM_EMAIL = "safetycar.dev@gmail.com";
    private static final String HTTP_ADDRESS = "http://localhost:8080";
    private static final String PATH = "/mypolicies/";

    private PolicyRepository policyRepository;
    private OfferRepository offerRepository;
    private UserService userService;
    private FileSaver fileSaver;
    private EmailSender emailSender;

    @Autowired
    public PolicyServiceImpl(PolicyRepository policyRepository, OfferRepository offerRepository, UserService userService, FileSaver fileSaver, EmailSender emailSender) {
        this.policyRepository = policyRepository;
        this.offerRepository = offerRepository;
        this.userService = userService;
        this.fileSaver = fileSaver;
        this.emailSender = emailSender;
    }

    //STATUS TYPES:
    // 1 PENDING
    // 2 ACCEPTED
    // 3 REJECTED
    // 4 CANCELLED

    @Override
    public Policy createPolicy(PolicyDTO policyDTO, MultipartFile imageFile) throws AuthenticationProblemException, InvalidTypeOfImageFileException, OfferDoesntExistException, InvalidDateException {

        checkIfEffectiveDateIsValid(policyDTO.getEffectiveDateOTP());

        String imageType;
        try {
            imageType = fileSaver.checkIfImageTypeIsValid(imageFile.getContentType().split("/")[1]);
        } catch (NullPointerException ex) {
            throw new InvalidTypeOfImageFileException(IMAGE_TYPE_EM);
        }

        Offer offer = getAndCheckIfOfferUuidIsValid(policyDTO.getOfferGUID());

        checkIfDriverAgeIsValid(policyDTO.getBirthDate(), offer.getDriverAge());

        User user = userService.getCurrentUser();

        StatusType statusType = policyRepository.getStatusById(1);

        Policy policy = new Policy();

        policy.setOffer(offer);
        policy.setStatus(statusType);
        policy.setUser(user);
        policy.setEffectiveDateOfThePolicy(policyDTO.getEffectiveDateOTP());
        policy.setImageOfVehicleRegistrationCertificate(fileSaver.saveFile(imageFile, imageType));
        policy.setFirstName(policyDTO.getFirstName());
        policy.setMiddleName(policyDTO.getMiddleName());
        policy.setLastName(policyDTO.getLastName());
        policy.setBirthDate(policyDTO.getBirthDate());
        policy.setEmail(policyDTO.getEmail());
        policy.setPhone(policyDTO.getPhone());
        policy.setPostalAddress(policyDTO.getPostalAddress());
        policy.setRegistrationNumberOfVehicle(policyDTO.getRegistrationNumberOfVehicle());

        policyRepository.createPolicy(policy);
        return policy;
    }

    public boolean checkIfDriverAgeIsValid(LocalDate birthDate, int driverAge) {
        int yearsOld = calculateYears(birthDate);
        if (yearsOld != driverAge) {
            throw new InvalidDateException(DRIVER_AGE_DOESNT_MATCH);
        } else {
            return true;
        }
    }


    public Offer getAndCheckIfOfferUuidIsValid(String offerGUID) {
        Offer offer = offerRepository.getByUUID(offerGUID);
        if (offer == null) {
            throw new OfferDoesntExistException(OFFER_GUID_NOT_EXISTS_EM);
        } else {
            return offer;
        }
    }

    public boolean checkIfEffectiveDateIsValid(LocalDate effectiveDateOTP) {
        if (effectiveDateOTP.isBefore(LocalDate.now())) {
            throw new InvalidDateException(EFFECTIVE_DATE_OF_THE_POLICY_EM);
        } else {
            return true;
        }
    }

    @Override
    public List<Policy> getCurrentUserPolicies(String policyStatus) throws AuthenticationProblemException, UserDoesntExistException {
        User user = userService.getCurrentUser();
        StatusType statusType = findPolicyByStatusType(policyStatus);

        List<Policy> policyList = policyRepository.getPoliciesByUserId(user.getId());

        if (policyStatus != null) {
            policyList = policyList.stream().filter(policy ->
                    policy.getStatus().getStatus().equals(statusType.getStatus()))
                    .collect(Collectors.toList());
        }
        return policyList;
    }

    @Override
    public List<Policy> getPendingPolicies() {
        return policyRepository.getPendingPolicies();
    }


    @Override
    public List<Policy> sortByDate() {
        List<Policy> policies = policyRepository.getAllPolicies();
        policies.sort(Comparator.comparing(Policy::getEffectiveDateOfThePolicy));
        return policies;
    }

    @Override
    public List<Policy> sortByDateUser(int id) {
        List<Policy> policies = policyRepository.getPoliciesByUserId(id);
        policies.sort(Comparator.comparing(Policy::getEffectiveDateOfThePolicy));
        return policies;
    }


    @Override
    public List<Policy> sortAlphabetically() {
        List<Policy> policies = policyRepository.getAllPolicies();
        policies.sort(Comparator.comparing(Policy::getLastName));
        return policies;
    }

    public Policy getPolicyForUser(int id) {
        User user = userService.getCurrentUser();
        List<Policy> currentUserPolicies = policyRepository.getPoliciesByUserId(user.getId());
        Policy policy = new Policy();
        for (Policy p : currentUserPolicies) {
            if (p.getPolicyId() == id) {
                policy = p;
                break;
            }
        }
        if (policy.getOffer() == null) {
            throw new PolicyDoesntExistException(YOU_HAVE_NO_SUCH_POLICY);
        }
        return policy;
    }

    @Override
    public Policy getPolicyById(int id) throws PolicyDoesntExistException {
        Policy policy = policyRepository.getPolicyById(id);
        if (policy == null) {
            throw new PolicyDoesntExistException(String.format(POLICY_DOESNT_EXIST_EM, id));
        }
        return policy;
    }

    @Override
    public Policy cancelMyPolicy(int id) throws AuthenticationProblemException, PolicyDoesntExistException {
        User user = userService.getCurrentUser();
        Policy policy = policyRepository.getPolicyById(id);
        if (policy == null) {
            throw new PolicyDoesntExistException(String.format(POLICY_DOESNT_EXIST_EM, id));
        }
        List<Policy> userPolicies = policyRepository.getPendingPoliciesByUserId(user.getId());
        List<Integer> userPoliciesIds = new ArrayList<>();
        for (Policy p : userPolicies) {
            userPoliciesIds.add(p.getPolicyId());
        }
        if (userPoliciesIds.contains(policy.getPolicyId())) {
            StatusType status = policyRepository.getStatusById(4);
            policy.setStatus(status);
            policyRepository.updatePolicy(policy);
            return policy;
        } else {
            throw new PolicyDoesntExistException(POLICY_DOESNT_EXIST_EM);
        }
    }

    @Override
    public Policy changePolicyStatus(int policyId, int statusId) throws AuthenticationProblemException, PolicyDoesntExistException {

        User admin = userService.getCurrentUser();
        Policy policy = policyRepository.getPolicyById(policyId);
        if (policy == null) {
            throw new PolicyDoesntExistException(String.format(POLICY_DOESNT_EXIST_EM, policyId));
        }
        StatusType status = policyRepository.getStatusById(statusId);
        policy.setAdmin(admin);
        policy.setStatus(status);
        policyRepository.updatePolicy(policy);

        String emailSubject = "Policy accepted.";
        String emailText = "Your policy was accepted! More about the policy here: ";
        if (statusId == 3) {
            emailSubject = "Policy rejected.";
            emailText = "Your policy was rejected! More about the policy here: ";
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(policy.getUser().getEmail());
        mailMessage.setSubject(emailSubject);
        mailMessage.setFrom(FROM_EMAIL);
        mailMessage.setText(emailText
                + HTTP_ADDRESS
                + PATH
                + policy.getPolicyId());
        emailSender.sendEmail(mailMessage);

        return policy;
    }

    @Override
    public Page<Policy> findPaginated(Pageable pageable, List<Policy> policies) {

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Policy> list;

        if (policies.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, policies.size());
            list = policies.subList(startItem, toIndex);
        }

        return new PageImpl<Policy>(list, PageRequest.of(currentPage, pageSize), policies.size());
    }

    private int calculateYears(LocalDate givenDate) {
        LocalDate today = LocalDate.now();
        long years = ChronoUnit.YEARS.between(givenDate, today);
        return (int) years;
    }

    @Override
    public List<Policy> getAllPolicies(String policyStatus, String username) {

        StatusType statusType = findPolicyByStatusType(policyStatus);
        User user = findPolicyByUsername(username);

        List<Policy> policyList = policyRepository.getAllPolicies();

        if (policyStatus != null) {
            policyList = policyList.stream().filter(policy ->
                    policy.getStatus().getStatus().equals(statusType.getStatus()))
                    .collect(Collectors.toList());
        }
        if (username != null) {
            policyList = policyList.stream()
                    .filter(policy -> policy.getEmail().equals(user.getEmail()))
                    .collect(Collectors.toList());
        }

        return policyList;


    }

    private StatusType findPolicyByStatusType(String policyStatus) {
        StatusType statusType = null;
        if (policyStatus != null) {
            statusType = policyRepository.findPolicyByStatus(policyStatus);
        }
        return statusType;
    }

    private User findPolicyByUsername(String username) {
        User user = null;
        if (username != null) {
            user = policyRepository.findPolicyByUsername(username);
        }
        return user;
    }

}
