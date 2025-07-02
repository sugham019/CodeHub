package com.example.backend.controller;

import com.example.backend.dto.CreateUserDto;
import com.example.backend.dto.LoginUserDto;
import com.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUserAccount(@RequestParam(required = false) String accessKey, @RequestBody CreateUserDto createUserDTO){
        userService.createUserAccount(createUserDTO, accessKey);
        return new ResponseEntity<>("Successfully created account", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserDto loginUserDTO){
        String token = userService.login(loginUserDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam String username, @RequestBody Map<String, String> password){
        userService.changePassword(username, password.get("old"), password.get("new"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
