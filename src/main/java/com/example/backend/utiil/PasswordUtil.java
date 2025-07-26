package com.example.backend.utiil;

import com.example.backend.exception.WeakPasswordException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public static String hashPassword(String password){
        return passwordEncoder.encode(password);
    }

    public static boolean matchPassword(String password, String passwordHash){
        return passwordEncoder.matches(password, passwordHash);
    }

    public static void validatePassword(String password){
        if (password.length() < 8) {
            throw new WeakPasswordException("Password must be at least 8 characters long.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new WeakPasswordException("Password must contain at least one uppercase letter.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new WeakPasswordException("Password must contain at least one lowercase letter.");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new WeakPasswordException("Password must contain at least one digit.");
        }
        if (!password.matches(".*[@#$%^&+=!].*")) {
            throw new WeakPasswordException("Password must contain at least one special character (@#$%^&+=!).");
        }
        if (password.matches(".*\\s.*")) {
            throw new WeakPasswordException("Password must not contain any whitespace.");
        }
    }

}
