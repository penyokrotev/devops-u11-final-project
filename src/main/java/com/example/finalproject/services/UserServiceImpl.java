package com.example.finalproject.services;

import com.example.finalproject.components.AuthenticationMediator;
import com.example.finalproject.exceptions.UserDoesntExistException;
import com.example.finalproject.models.SecurityUser;
import com.example.finalproject.models.User;
import com.example.finalproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String EMAIL_ALREADY_REGISTERED_EM = "Email address %s is already registered";
    private static final String USER_DOESNT_EXIST_EM = "User with username %s doesn't exist";

    private UserRepository userRepository;
    private AuthenticationMediator authenticationMediator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationMediator authenticationMediator) {
        this.userRepository = userRepository;
        this.authenticationMediator = authenticationMediator;
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User getById(int id) throws UserDoesntExistException {
        User userById = userRepository.getById(id);
        if (userById == null) {
            throw new UserDoesntExistException(String.format(USER_DOESNT_EXIST_EM, id));
        }
        return userById;
    }

    @Override
    public User getByName(String email) {
        try {
            User user = userRepository.getByName(email);
            if (user == null) {

                throw new IllegalArgumentException(String.format(USER_DOESNT_EXIST_EM, email));
            }
            return user;
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, e.getMessage());
        }
    }

    @Override
    public User getCurrentUser() {
        return getByName(authenticationMediator.getCurrentAuthenticatedUser());
    }

    @Override
    public SecurityUser getSecurityUserByEmail(String email) {
       return userRepository.getSecurityUserByEmail(email);
    }

    @Override
    public SecurityUser updateSecurityUser(String email, boolean enabled) {
        SecurityUser securityUser = userRepository.getSecurityUserByEmail(email);
        securityUser.setEnabled(enabled);
        userRepository.updateSecurityUser(securityUser);
        return securityUser;
    }

    @Override
    public void create(User user) {
        List<User> users = userRepository.getAll().stream()
                .filter(user1 -> user1.getEmail().equals(user.getEmail()))
                .collect(Collectors.toList());

        if (users.size() > 0) {
            throw new IllegalArgumentException((String.format(EMAIL_ALREADY_REGISTERED_EM, user.getEmail())));
        }

        userRepository.create(user);
    }

    @Override
    public void update(User user) {
        User newUser = getCurrentUser();
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setMiddleName(user.getMiddleName());
        newUser.setLastName(user.getLastName());
        newUser.setBirthDate(user.getBirthDate());
        newUser.setPicture(user.getPicture());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setPostalAddress(user.getPostalAddress());
        userRepository.update(newUser);
    }


    public Page<User> findPaginated(Pageable pageable, List<User> users) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<User> list;

        if (users.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, users.size());
            list = users.subList(startItem, toIndex);
        }

        return new PageImpl<User>(list, PageRequest.of(currentPage, pageSize), users.size());
    }
}

