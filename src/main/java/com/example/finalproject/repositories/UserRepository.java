package com.example.finalproject.repositories;

import com.example.finalproject.models.SecurityUser;
import com.example.finalproject.models.User;

import java.util.List;

public interface UserRepository {

    List<User> getAll();

    User getById(int id);

    User getByName(String email);

    SecurityUser getSecurityUserByEmail(String email);

    void updateSecurityUser(SecurityUser securityUser);

    void create(User user);

    void update(User user);

 //   void delete(int id);


}
