package com.example.backend.dto;

public class ForgotPasswordDto {
    private String email;
    private String verificationCode;
    private String newPassword;

    public ForgotPasswordDto(){

    }

    public String getEmail() {
        return email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
