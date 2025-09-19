package com.example.backend.controller.api;

import com.example.backend.dto.CreateUserDto;
import com.example.backend.dto.ForgotPasswordDto;
import com.example.backend.dto.ImageDto;
import com.example.backend.dto.LoginUserDto;
import com.example.backend.model.UserPrincipal;
import com.example.backend.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/profilePicture")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("profilePicture") MultipartFile profilePictureImage,
                                                       @AuthenticationPrincipal UserPrincipal userDetails){
        userService.uploadProfilePicture(userDetails.getUsername(), profilePictureImage);
        return new ResponseEntity<>("Successfully updated profile picture ", HttpStatus.OK);
    }

    @GetMapping("/profilePicture/{id}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable long id) {
        ImageDto image = userService.getProfilePicture(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.type()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + image.name() + "\"")
                .body(image.data());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserDto loginUserDTO){
        String token = userService.login(loginUserDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/contact")
    public ResponseEntity<Void> contact(@RequestParam String name, @RequestParam String email,
                                          @RequestBody String message){
        userService.contact(name, email, message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Void> changePassword(@RequestParam String email, @RequestBody Map<String, String> password){
        userService.changePassword(email, password.get("old"), password.get("new"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordDto request) {
        userService.resetPassword(request.getEmail(), request.getVerificationCode(), request.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/ratings")
    public ResponseEntity<Integer> ratings(@AuthenticationPrincipal UserPrincipal userInfo){
        int ratings = userService.getRatings(userInfo.getUsername());
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

}
