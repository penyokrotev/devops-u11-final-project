package com.example.finalproject.services;

import com.example.finalproject.exceptions.UserDoesntExistException;
import com.example.finalproject.models.SecurityUser;
import com.example.finalproject.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(int id) throws UserDoesntExistException;

    User getByName(String email);

    User getCurrentUser();

    SecurityUser getSecurityUserByEmail(String email);

    SecurityUser updateSecurityUser(String email, boolean enabled);

    void create(User user);

    void update(User user);

    Page<User> findPaginated(Pageable pageable, List<User> users);

  //  void delete(int id);

}
