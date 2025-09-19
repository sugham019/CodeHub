package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.model.Problem;
import com.example.backend.model.Tag;
import com.example.backend.model.TagType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface UserService {

    void createAndSendVerificationCode(String userEmail);

    void createUserAccount(CreateUserDto createUserDTO, String accessKey);

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
