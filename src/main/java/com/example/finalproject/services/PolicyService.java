package com.example.finalproject.services;

import com.example.finalproject.exceptions.*;
import com.example.finalproject.models.Policy;
import com.example.finalproject.models.PolicyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PolicyService {

    Policy createPolicy(PolicyDTO policyDTO, MultipartFile imageFile)
            throws AuthenticationProblemException, InvalidTypeOfImageFileException, OfferDoesntExistException, InvalidDateException;

    List<Policy> getCurrentUserPolicies(String policyStatus) throws AuthenticationProblemException, UserDoesntExistException;

    List<Policy> getPendingPolicies();

    List<Policy> getAllPolicies(String policyStatus, String username);

    List<Policy> sortByDate();

    List<Policy> sortByDateUser(int id);

    List<Policy> sortAlphabetically();

    Policy getPolicyForUser(int id);

    Policy getPolicyById(int id) throws PolicyDoesntExistException;

    Policy cancelMyPolicy(int id) throws AuthenticationProblemException, PolicyDoesntExistException;

    Policy changePolicyStatus(int policyId, int statusId) throws AuthenticationProblemException, PolicyDoesntExistException;

    Page<Policy> findPaginated(Pageable pageable, List<Policy> policies);

//    List<Policy> showPolicies(String policyStatus, String username);

}
