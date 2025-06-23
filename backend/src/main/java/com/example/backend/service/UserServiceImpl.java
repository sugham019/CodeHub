package com.example.backend.service;

import com.example.backend.dto.CreateUserDto;
import com.example.backend.dto.LoginUserDto;
import com.example.backend.exception.*;
import com.example.backend.model.AccessLevel;
import com.example.backend.model.Leaderboard;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.utiil.JwtUtil;
import com.example.backend.utiil.PasswordUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Value("${access_key}")
    private String accessKey;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
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
        if(!PasswordUtil.isPasswordStrong(password)){
            throw new WeakPasswordException(password);
        }
        // Todo: birth date validation
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

}
