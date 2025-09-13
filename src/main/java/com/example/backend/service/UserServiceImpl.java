package com.example.backend.service;

import com.example.backend.dto.CreateUserDto;
import com.example.backend.dto.ImageDto;
import com.example.backend.dto.LoginUserDto;
import com.example.backend.dto.UserDto;
import com.example.backend.exception.*;
import com.example.backend.model.*;
import com.example.backend.repository.UserRepository;
import com.example.backend.util.JwtUtil;
import com.example.backend.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.Period;
import java.time.LocalDate;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final LeaderboardService leaderboardService;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

    private final StringRedisTemplate redisTemplate;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${access_key}")
    private String accessKey;

    public UserServiceImpl(UserRepository userRepository, LeaderboardService leaderboardService,
                           EmailService emailService, StringRedisTemplate redisTemplate, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.leaderboardService = leaderboardService;
        this.emailService = emailService;
        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
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
        if(userRepository.existsByEmail(userEmail)){
            throw new UserAccountException(userEmail+" is already associated with an account");
        }
        String verificationCode = PasswordUtil.generateOTP(6);
        redisTemplate.opsForValue().set(userEmail, verificationCode, Duration.ofMinutes(5));

        String[] recipient = {userEmail};
        emailService.sendEmail(recipient, "CodeHub Verification Code", verificationCode);
    }

    @Override
    public void createUserAccount(CreateUserDto createUserDTO, String givenAccessKey) {
        String email = createUserDTO.getEmail();
        String password = createUserDTO.getPassword();
        String displayName = createUserDTO.getDisplayName();
        LocalDate birthDate = createUserDTO.getBirthDate();
        String storedVerificationCode = redisTemplate.opsForValue().get(createUserDTO.getEmail());
        if(storedVerificationCode == null || !storedVerificationCode.equals(createUserDTO.getVerificationCode())){
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
        redisTemplate.delete(storedVerificationCode);
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
        userRepository.findByEmail(email).ifPresent(user -> {
            String newPasswordHash = PasswordUtil.hashPassword(newPassword);
            user.setPasswordHash(newPasswordHash);
            userRepository.save(user);
        });
    }

    @Override
    public ImageDto getProfilePicture(long id) {
        User user = getUser(id);
        if(user.getProfilePictureImage() == null){
            ClassPathResource defaultPfp = new ClassPathResource("static/img/pfp.png");
            try {
                byte[] imageData = defaultPfp.getInputStream().readAllBytes();
                return new ImageDto(imageData, "pfp.png", "image/png");
            } catch (IOException e) {
                log.warn("Failed to retrieve default profile picture", e);
                throw new RuntimeException("Error retrieving profile picture for : " + user.getEmail());
            }
        }
        return new ImageDto(user.getProfilePictureImage(), user.getProfilePictureImageName(), user.getProfilePictureImageType());
    }

    @Override
    public void uploadProfilePicture(String email, MultipartFile profilePictureImage) {
        User user = getUser(email);
        user.setProfilePictureImageName(profilePictureImage.getName());
        user.setProfilePictureImageType(profilePictureImage.getContentType());
        try {
            user.setProfilePictureImage(profilePictureImage.getBytes());
        } catch (IOException e) {
            log.warn("Failed to upload profile picture for {}:", user.getEmail(), e);
            throw new RuntimeException("Error updating profile picture image information");
        }
        userRepository.save(user);
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
        return new UserDto(user.getId(), user.getEmail(), user.getDisplayName(), user.getLeaderboard().getRating(), user.getRecentProblemId());
    }

}
