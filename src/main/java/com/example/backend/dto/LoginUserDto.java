package com.example.backend.dto;

public class LoginUserDto {

    private String email;
    private String password;

    public LoginUserDto(){

    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

}
