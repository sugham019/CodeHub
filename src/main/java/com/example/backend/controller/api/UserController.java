package com.example.backend.controller.api;

import com.example.backend.dto.CreateUserDto;
import com.example.backend.dto.LoginUserDto;
import com.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/verification-code")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email){
        userService.createAndSendVerificationCode(email);
        return new ResponseEntity<>("Sent verification code to the user", HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUserAccount(@RequestParam(required = false) String accessKey, @RequestBody CreateUserDto createUserDTO){
        userService.createUserAccount(createUserDTO, accessKey);
        return new ResponseEntity<>("Successfully created user account", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserDto loginUserDTO){
        String token = userService.login(loginUserDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam String email, @RequestBody Map<String, String> password){
        userService.changePassword(email, password.get("old"), password.get("new"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
