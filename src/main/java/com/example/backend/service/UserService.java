package com.example.backend.service;

import com.example.backend.dto.CreateUserDto;
import com.example.backend.dto.LoginUserDto;
import com.example.backend.dto.UserDto;
import com.example.backend.model.Problem;
import com.example.backend.model.User;

public interface UserService {

    void createAndSendVerificationCode(String userEmail);

    void createUserAccount(CreateUserDto createUserDTO, String accessKey);

    String login(LoginUserDto loginUserDTO);

    void changePassword(String username, String oldPassword, String newPassword);

    User getUser(String email);

    UserDto getBasicUserInfo(String email);

    void solveProblem(User user, Problem problem);

}
