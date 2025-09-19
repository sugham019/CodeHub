package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.exception.*;
import com.example.backend.model.*;
import com.example.backend.repository.UserRepository;
import com.example.backend.util.JwtUtil;
import com.example.backend.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.Period;
import java.time.LocalDate;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final LeaderboardService leaderboardService;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

    private final StringRedisTemplate redisTemplate;

    private final ProblemService problemService;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${access_key}")
    private String accessKey;

    public UserServiceImpl(UserRepository userRepository, LeaderboardService leaderboardService,
                           EmailService emailService, StringRedisTemplate redisTemplate, AuthenticationManager authenticationManager,
                           ProblemService problemService){
        this.userRepository = userRepository;
        this.leaderboardService = leaderboardService;
        this.emailService = emailService;
        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
        this.problemService = problemService;
    }

    private void validateSignupInformation(String email, String password, String displayName, LocalDate birthDate){
        if(email == null || password == null
                || displayName == null || birthDate == null){
            throw new IncompleteInformationException();
        }
        PasswordUtil.validatePassword(password);
        LocalDate today = LocalDate.now();
        if(birthDate.isAfter(today)){
            throw new InvalidDateException("Date cannot be future");
        }
        int age = Period.between(birthDate, today).getYears();
        if (age < 5) {
            throw new InvalidDateException("Too young! Age must be at least 5 years.");
        } else if (age > 100) {
            throw new InvalidDateException("Too old! Age must be less than or equal to 100 years.");
        }
    }

    @Override
    public void createAndSendVerificationCode(String userEmail) {
        String verificationCode = PasswordUtil.generateOTP(6);
        redisTemplate.opsForValue().set(userEmail, verificationCode, Duration.ofMinutes(5));

        String[] recipient = {userEmail};
        emailService.sendEmail(recipient, "CodeHub Verification Code", verificationCode);
    }

    @Override
    public void createUserAccount(CreateUserDto createUserDTO, String givenAccessKey) {
        String email = createUserDTO.getEmail();
        if(userRepository.existsByEmail(email)){
            throw new UserAccountException(email+" is already associated with an account");
        }
        String password = createUserDTO.getPassword();
        String displayName = createUserDTO.getDisplayName();
        LocalDate birthDate = createUserDTO.getBirthDate();
        String storedVerificationCode = redisTemplate.opsForValue().get(createUserDTO.getEmail());
        if (!Objects.equals(storedVerificationCode, createUserDTO.getVerificationCode())) {
            throw new UserAccountException("Invalid verification code");
        }
        validateSignupInformation(email, password, displayName, birthDate);

        String passwordHash = PasswordUtil.hashPassword(password);
        AccessLevel accessLevel = (givenAccessKey != null && givenAccessKey.equals(accessKey))? createUserDTO.getAccessLevel(): AccessLevel.GENERAL;
        Leaderboard leaderboard = new Leaderboard(0);
        User user = new User(email, displayName, passwordHash, birthDate, accessLevel);
        leaderboard.setUser(user);
        user.setLeaderboard(leaderboard);
        userRepository.save(user);
        redisTemplate.delete(email);
    }

    @Override
    public String login(LoginUserDto loginUserDTO) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(), loginUserDTO.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid email or password");
        }
        return JwtUtil.generateToken(loginUserDTO.getEmail());
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, oldPassword)
            );
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid email or password");
        }
        PasswordUtil.validatePassword(newPassword);
        User user = getUser(email);
        String newPasswordHash = PasswordUtil.hashPassword(newPassword);
        user.setPasswordHash(newPasswordHash);
        userRepository.save(user);
    }

    @Override
    public void resetPassword(String email, String otp, String newPassword) {
        String storedVerificationCode = redisTemplate.opsForValue().get(email);
        if (!Objects.equals(storedVerificationCode, otp)) {
            throw new UserAccountException("Invalid verification code");
        }
        PasswordUtil.validatePassword(newPassword);
        User user = getUser(email);
        String newPasswordHash = PasswordUtil.hashPassword(newPassword);
        user.setPasswordHash(newPasswordHash);
        userRepository.save(user);
        redisTemplate.delete(email);
    }

    @Override
    public ImageDto getProfilePicture(long id) {
        User user = getUser(id);
        String profilePicturePath = user.getProfilePicturePath();
        String profilePictureType = user.getProfilePictureType();

        Path file;
        if(profilePicturePath != null && !profilePicturePath.isBlank()) {
            file = Paths.get("uploads/profile-pictures").resolve(profilePicturePath);
        } else {
            file = Paths.get("default/pfp.png");
            profilePictureType = "image/png";
        }
        try {
            byte[] imageData = Files.readAllBytes(file);
            String filename = file.getFileName().toString();
            return new ImageDto(imageData, filename, profilePictureType);
        } catch (IOException e) {
            log.warn("Failed to retrieve profile picture for {}", user.getEmail(), e);
            throw new RuntimeException("Error retrieving profile picture for : " + user.getEmail());
        }
    }

    @Override
    public void uploadProfilePicture(String email, MultipartFile file) {
        User user = getUser(email);
        try {
            String filename = user.getId() + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path uploadDir = Paths.get("uploads/profile-pictures");
            if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);

            Path filePath = uploadDir.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            user.setProfilePicturePath(filename);
            user.setProfilePictureType(file.getContentType());
            userRepository.save(user);
        }catch (IOException e){
            throw new RuntimeException("Error uploading profile picture for : " + user.getEmail());
        }
    }

    @Override
    public void setRecentProblemId(String email, String problemId) {
        User user = getUser(email);
        user.setRecentProblemId(problemId);
        userRepository.save(user);
    }

    @Override
    public void solveProblem(String email, Problem problem) {
        User user = getUser(email);
        boolean alreadySolved = user.getSolvedProblems().stream()
                .anyMatch(sp -> sp.getId().getProblemId().equals(problem.getId()));
        if(alreadySolved) return;

        LocalDate currDate = LocalDate.now();
        SolvedProblem solvedProblem = new SolvedProblem(problem.getId(), user.getId(), problem.getTitle(), currDate);
        user.getSolvedProblems().add(solvedProblem);
        userRepository.save(user);
        leaderboardService.updateRating(user, problem.getDifficulty().getValue());
    }

    @Override
    public ProblemCountDto getSolvedProblems(long userId) {
        User user = getUser(userId);
        long easyCount = 0, mediumCount = 0, hardCount = 0;
        for(SolvedProblem solvedProblem: user.getSolvedProblems()){
            String problemId = solvedProblem.getId().getProblemId();
            Problem problem = problemService.getProblemById(problemId);

            if (problem.getDifficulty() == Difficulty.EASY){
                easyCount++;
            }else if(problem.getDifficulty() == Difficulty.MEDIUM){
                mediumCount++;
            }else if(problem.getDifficulty() == Difficulty.HARD){
                hardCount++;
            }
        }
        return new ProblemCountDto(easyCount, mediumCount, hardCount);
    }

    @Override
    public void contact(String name, String email, String message) {
        String[] userEmail = {email};
        String[] teamEmail = {"sughamkharel123@gmail.com", "sambhav2468@gmail.com"};

        String msg = "From user: " + name + "\nEmail: " + email + "\nMessage" + message;
        emailService.sendEmail(teamEmail, "Received a inquiry", msg);

        String replyMessage = "Hello " + name + ",\n Our team has received your message. We will get back to you shortly.";
        emailService.sendEmail(userEmail, "About your inquiry", replyMessage);
    }

    @Override
    public Set<Tag> getTags(String email, TagType tagType) {
        return userRepository.findTagsByUserEmailAndTagType(email, tagType);
    }

    @Override
    public void addTag(String email, TagType tagType, String name) {
        User user = getUser(email);
        Tag tag = new Tag(tagType, name);
        user.getTags().add(tag);
        userRepository.save(user);
    }

    @Override
    public int getRatings(String email) {
        return getUser(email).getLeaderboard().getRating();
    }

    @Override
    public int getProfileViews(long userId) {
        User user = getUser(userId);
        return user.getTotalProfileViews();
    }

    @Override
    public void viewedProfile(long userId) {
        User user = getUser(userId);
        user.setTotalProfileViews(user.getTotalProfileViews() + 1);
        userRepository.save(user);
    }

    private User getUser(long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: "+id));
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: "+email));
    }

    @Override
    public UserDto getBasicUserInfo(String email) {
        User user = getUser(email);
        return new UserDto(user.getId(), user.getEmail(), user.getDisplayName(), user.getLeaderboard().getRating(), user.getRecentProblemId(), user.getBirthDate());
    }

    @Override
    public UserDto getBasicUserInfo(long id) {
        User user = getUser(id);
        return new UserDto(user.getId(), user.getEmail(), user.getDisplayName(), user.getLeaderboard().getRating(), user.getRecentProblemId(), user.getBirthDate());
    }

}
