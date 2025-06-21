package com.example.backend.controller;

import com.example.backend.dto.CreateUserDTO;
import com.example.backend.dto.LoginUserDTO;
import com.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUserAccount(@RequestParam(required = false) String accessKey, @RequestBody CreateUserDTO createUserDTO){
        userService.createUserAccount(createUserDTO, accessKey);
        return new ResponseEntity<>("Successfully created account", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserDTO loginUserDTO){
        String token = userService.login(loginUserDTO);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

}
