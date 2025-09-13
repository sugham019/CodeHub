package com.example.backend.service;

import com.example.backend.dto.CreateUserDto;
import com.example.backend.dto.ImageDto;
import com.example.backend.dto.LoginUserDto;
import com.example.backend.dto.UserDto;
import com.example.backend.model.Problem;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void createAndSendVerificationCode(String userEmail);

    void createUserAccount(CreateUserDto createUserDTO, String accessKey);

    String login(LoginUserDto loginUserDTO);

    void changePassword(String username, String oldPassword, String newPassword);

    ImageDto getProfilePicture(long id);

    void uploadProfilePicture(String email, MultipartFile profilePictureImage);

    void setRecentProblemId(String email, String problemId);

    UserDto getBasicUserInfo(String email);

    void solveProblem(String email, Problem problem);

}
