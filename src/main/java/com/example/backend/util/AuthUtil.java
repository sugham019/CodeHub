package com.example.backend.util;

import org.springframework.stereotype.Component;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthUtil {

    public static void deleteCookie(String name, HttpServletResponse response){
        Cookie clearCookie = new Cookie(name, "");
        clearCookie.setPath("/");
        clearCookie.setMaxAge(0);
        response.addCookie(clearCookie);
    }
}
