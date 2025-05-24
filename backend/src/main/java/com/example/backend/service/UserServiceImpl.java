package com.example.backend.service;

import com.example.backend.dto.CreateUserDTO;
import com.example.backend.dto.LoginUserDTO;
import com.example.backend.exception.IncompleteInformationException;
import com.example.backend.exception.UserAlreadyExistsException;
import com.example.backend.exception.WeakPasswordException;
import com.example.backend.model.Leaderboard;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.utiil.PasswordUtil;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(CreateUserDTO createUserDTO) {
        if(createUserDTO.getUsername() == null || createUserDTO.getPassword() == null
            || createUserDTO.getDisplayName() == null || createUserDTO.getBirthDate() == null){
            throw new IncompleteInformationException();
        }
        String username = createUserDTO.getUsername();
        if(userRepository.existsById(username)){
            throw new UserAlreadyExistsException(username);
        }
        String password = createUserDTO.getPassword();
        if(!PasswordUtil.isPasswordStrong(password)){
            throw new WeakPasswordException(password);
        }
        String passwordHash = PasswordUtil.hashPassword(password);
        Leaderboard leaderboard = new Leaderboard(0);
        User user = new User(username, createUserDTO.getDisplayName(), passwordHash, createUserDTO.getBirthDate());
        user.setLeaderboard(leaderboard);
        leaderboard.setUser(user);
        userRepository.save(user);
    }

    // Todo
    @Override
    public void login(LoginUserDTO loginUserDTO) {

    }

}
