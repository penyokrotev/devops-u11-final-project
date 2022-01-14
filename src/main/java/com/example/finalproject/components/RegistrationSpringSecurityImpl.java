package com.example.finalproject.components;

import com.example.finalproject.exceptions.VerificationEmailExpiredException;
import com.example.finalproject.models.ConfirmationToken;
import com.example.finalproject.models.SecurityUser;
import com.example.finalproject.models.User;
import com.example.finalproject.models.UserReg;
import com.example.finalproject.repositories.ConfirmationTokenRepository;
import com.example.finalproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Component
public class RegistrationSpringSecurityImpl implements RegistrationSpringSecurity {
    private static final String DEFAULT_USER_AVATAR_DIRECTORY = "../avatars/defAvatar.png";
    private static final String VERIFICATION_EXPIRED_EM = "Verification Email Expired Please try registering again and verify in the next 24h!";
    private static final String HTTP_ADDRESS = "http://localhost:8080";
    private static final String PATH = "/verification/confirm-account?token=";
    private static final String FROM_EMAIL = "safetycar.dev@gmail.com";
    private static final String COMPLETE_REGISTRATION = "Complete Registration!";
    private static final String TO_CONFIRM_ACCOUNT = "To confirm your account, please click here : ";

    private UserService userService;
    private UserDetailsManager userDetailsManager;
    private EmailSender emailSender;
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public RegistrationSpringSecurityImpl(UserDetailsManager userDetailsManager, UserService userService, EmailSender emailSender, ConfirmationTokenRepository confirmationTokenRepository) {
        this.userDetailsManager = userDetailsManager;
        this.userService = userService;
        this.emailSender = emailSender;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public boolean checkIfEmailIsAlreadyRegistered(String email) {
       return userDetailsManager.userExists(email);
    }

    public void registerUser(UserReg userReg) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encoder = passwordEncoder.encode(userReg.getPassword());

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        org.springframework.security.core.userdetails.User newUser =
                new org.springframework.security.core.userdetails.User(
                        userReg.getEmail(),
                        "{bcrypt}"+ encoder,
                        authorities);
        userDetailsManager.createUser(newUser);

        SecurityUser securityUser =  userService.updateSecurityUser(userReg.getEmail(), false);

        ConfirmationToken confirmationToken = new ConfirmationToken(securityUser);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(securityUser.getUsername());
        mailMessage.setSubject(COMPLETE_REGISTRATION);
        mailMessage.setFrom(FROM_EMAIL);
        mailMessage.setText(TO_CONFIRM_ACCOUNT
                + HTTP_ADDRESS
                + PATH
                + confirmationToken.getConfirmationToken());
        emailSender.sendEmail(mailMessage);
    }

    @Override
    public void confirmAccount(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.getByConfirmationToken(confirmationToken);
            if (token != null) {
                userService.updateSecurityUser(token.getUser().getUsername(), true);
                User userDetails = new User();
                userDetails.setEmail(token.getUser().getUsername());
                userDetails.setBirthDate(LocalDate.of(1111, Month.NOVEMBER,11));
                userDetails.setPicture(DEFAULT_USER_AVATAR_DIRECTORY);

                userService.create(userDetails);
            } else {
                throw new VerificationEmailExpiredException(VERIFICATION_EXPIRED_EM);
            }
    }
}
