package com.example.backend.service;

import com.example.backend.dto.CreateUserDto;
import com.example.backend.dto.LoginUserDto;
import com.example.backend.exception.*;
import com.example.backend.model.*;
import com.example.backend.repository.UserRepository;
import com.example.backend.utiil.JwtUtil;
import com.example.backend.utiil.PasswordUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.time.Period;
import java.time.LocalDate;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final LeaderboardService leaderboardService;

    private final AuthenticationManager authenticationManager;

    @Value("${access_key}")
    private String accessKey;

    public UserServiceImpl(UserRepository userRepository, LeaderboardService leaderboardService,
                           AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.leaderboardService = leaderboardService;
        this.authenticationManager = authenticationManager;
    }

    private void validateSignupInformation(String username, String password, String displayName, LocalDate birthDate){
        if(username == null || password == null
                || displayName == null || birthDate == null){
            throw new IncompleteInformationException();
        }
        if(userRepository.existsById(username)){
            throw new UserAlreadyExistsException(username);
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
    public void createUserAccount(CreateUserDto createUserDTO, String givenAccessKey) {
        String username = createUserDTO.getUsername();
        String password = createUserDTO.getPassword();
        String displayName = createUserDTO.getDisplayName();
        LocalDate birthDate = createUserDTO.getBirthDate();
        validateSignupInformation(username, password, displayName, birthDate);

        String passwordHash = PasswordUtil.hashPassword(password);
        AccessLevel accessLevel = (givenAccessKey != null && givenAccessKey.equals(accessKey))? createUserDTO.getAccessLevel(): AccessLevel.GENERAL;
        Leaderboard leaderboard = new Leaderboard(0);
        User user = new User(username, displayName, passwordHash, birthDate, accessLevel);
        leaderboard.setUser(user);
        user.setLeaderboard(leaderboard);
        userRepository.save(user);
    }

    @Override
    public String login(LoginUserDto loginUserDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword())
        );
        if(!authentication.isAuthenticated()){
            throw new BadCredentialsException("Invalid username/password");
        }
        return JwtUtil.generateToken(authentication.getName());
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, oldPassword)
        );
        if(!authentication.isAuthenticated()){
            throw new BadCredentialsException("Invalid username/password");
        }
        PasswordUtil.validatePassword(newPassword);
        userRepository.findById(username).ifPresent(user -> {
            String newPasswordHash = PasswordUtil.hashPassword(newPassword);
            user.setPasswordHash(newPasswordHash);
            userRepository.save(user);
        });
    }

    @Override
    public User getUser(String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: "+username));
    }

    @Override
    public void solveProblem(User user, Problem problem) {
        boolean alreadySolved = user.getSolvedProblems().stream()
                .anyMatch(sp -> sp.getId().getProblemId().equals(problem.getId()));
        if(alreadySolved) return;

        LocalDate currDate = LocalDate.now();
        SolvedProblem solvedProblem = new SolvedProblem(problem.getId(), user.getUsername(), problem.getTitle(), currDate);
        user.getSolvedProblems().add(solvedProblem);
        userRepository.save(user);
        leaderboardService.updateRating(user, problem.getDifficulty().getValue());
    }

}
