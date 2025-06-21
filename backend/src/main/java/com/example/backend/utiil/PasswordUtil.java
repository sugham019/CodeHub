package com.example.backend.utiil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public static String hashPassword(String password){
        return passwordEncoder.encode(password);
    }

    public static boolean matchPassword(String password, String passwordHash){
        return passwordEncoder.matches(password, passwordHash);
    }

    // Todo
    public static boolean isPasswordStrong(String password){
        return true;
    }

}
