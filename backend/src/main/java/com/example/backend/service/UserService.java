package com.example.backend.service;

import com.example.backend.dto.CreateUserDTO;
import com.example.backend.dto.LoginUserDTO;

public interface UserService {

    void createUser(CreateUserDTO createUserDTO);
    void login(LoginUserDTO loginUserDTO);

}
