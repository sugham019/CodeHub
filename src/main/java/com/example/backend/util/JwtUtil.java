package com.example.backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

public class JwtUtil {

    private final SecretKey key;
    private static final JwtUtil instance = new JwtUtil();

    public JwtUtil(){
        this.key = generateKey();
    }

    public static String generateToken(String username){
        return Jwts.builder()
                .claims()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .and()
                .signWith(instance.key)
                .compact();
    }

    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()));
    }

    private SecretKey generateKey(){
        KeyGenerator keyGenerator;
        try{
            keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        SecretKey secretKey = keyGenerator.generateKey();
        String secretKeyStr = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyStr);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(instance.key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
