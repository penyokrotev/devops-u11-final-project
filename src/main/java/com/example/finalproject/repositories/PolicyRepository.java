package com.example.finalproject.repositories;

import com.example.finalproject.models.Policy;
import com.example.finalproject.models.StatusType;
import com.example.finalproject.models.User;

import java.util.List;

public interface PolicyRepository {

    StatusType getStatusById(int id);

    void createPolicy(Policy policy);

    List<Policy> getPendingPolicies();

    List<Policy> getAllPolicies();


    Policy getPolicyById(int id);

    List<Policy> getPendingPoliciesByUserId(int id);

    void updatePolicy(Policy policy);

    List<Policy> getPoliciesByUserId(int id);

    StatusType findPolicyByStatus(String name);

    User findPolicyByUsername(String name);
}
