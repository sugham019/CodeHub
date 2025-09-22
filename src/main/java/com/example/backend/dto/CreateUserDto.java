package com.example.backend.dto;

import com.example.backend.model.AccessLevel;

import java.time.LocalDate;

public class CreateUserDto {

    private String verificationCode;
    private String email;
    private String displayName;
    private String password;
    private LocalDate birthDate;
    private AccessLevel accessLevel;

    public CreateUserDto(){

    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getEmail() {
        return email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public String getPassword(){
        return password;
    }

    public String getDisplayName(){
        return displayName;
    }

    public LocalDate getBirthDate(){
        return birthDate;
    }

    public AccessLevel getAccessLevel(){
        return accessLevel;
    }

}
