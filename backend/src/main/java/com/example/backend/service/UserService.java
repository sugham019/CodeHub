package com.example.backend.service;

import com.example.backend.dto.CreateUserDTO;
import com.example.backend.dto.LoginUserDTO;

public interface UserService {

    void createUserAccount(CreateUserDTO createUserDTO, String accessKey);
    String login(LoginUserDTO loginUserDTO);

}
