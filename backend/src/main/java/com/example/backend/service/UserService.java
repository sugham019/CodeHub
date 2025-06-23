package com.example.backend.service;

import com.example.backend.dto.CreateUserDto;
import com.example.backend.dto.LoginUserDto;

public interface UserService {

    void createUserAccount(CreateUserDto createUserDTO, String accessKey);
    String login(LoginUserDto loginUserDTO);

}
