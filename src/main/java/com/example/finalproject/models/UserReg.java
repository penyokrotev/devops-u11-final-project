package com.example.finalproject.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserReg {

    @Email(message = "Please enter correct email!")
    @NotEmpty
    private String email;

    @Pattern(regexp = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{6,16})", message = "Your password must contain" +
            "at least one capital letter," +
            "one digit" +
            "and length between 6 and 16 symbols!")
    @NotEmpty
    private String password;
//
//    @NotEmpty
//    @Pattern(regexp = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{6,16})", message = "Your password must contain" +
//            "at least one capital letter," +
//            "one digit" +
//            "and length between 6 and 16 symbols!")
//    private String confirmPassword;


    public UserReg() {
    }

    public UserReg(@Email(message = "Please enter correct email!") @NotEmpty String email, @Pattern(regexp = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{6,16})", message = "Your password must contain" +
            "at least one capital letter," +
            "one digit" +
            "and length between 6 and 16 symbols!") @NotEmpty String password) {
        this.email = email;
        this.password = password;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getConfirmPassword() {
//        return confirmPassword;
//    }
//
//    public void setConfirmPassword(String confirmPassword) {
//        this.confirmPassword = confirmPassword;
//    }
}
