package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.model.Problem;
import com.example.backend.model.Tag;
import com.example.backend.model.TagType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface UserService {

    String createAndSendVerificationCode(String userEmail, boolean storeTemporarily);

    void createUserAccount(CreateUserDto createUserDTO, String accessKey) throws JsonProcessingException;

    void completeUserAccountCreation(String email, String verificationCode) throws JsonProcessingException;

    String login(LoginUserDto loginUserDTO);

    void changePassword(String email, String oldPassword, String newPassword);

    void resetPassword(String email, String otp, String newPassword);

    ImageDto getProfilePicture(long id);

    void uploadProfilePicture(String email, MultipartFile profilePictureImage);

    void setRecentProblemId(String email, String problemId);

    UserDto getBasicUserInfo(String email);

    UserDto getBasicUserInfo(long id);

    void solveProblem(String email, Problem problem);

    ProblemCountDto getSolvedProblems(long userId);

    void contact(String name,  String email, String message);

    Set<Tag> getTags(String email, TagType tagType);

    void addTag(String email, TagType tagType, String name);

    int getRatings(String email);

    int getProfileViews(long userId);

    void viewedProfile(long userId);

}
