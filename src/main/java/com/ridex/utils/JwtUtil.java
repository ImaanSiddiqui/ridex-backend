package com.ridex.utils;


import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ridex.entity.UserEntity;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
	
	private static final Dotenv dotenv = Dotenv.load();

    private static final String SECRET_KEY = dotenv.get("SECRET_KEY");
    private final JwtParser jwtParser;

    public JwtUtil() {
        this.jwtParser = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .build();
    }

    // ✅ Generate JWT for User
//    public String generateToken(UserEntity user) {
//        return Jwts.builder()
//                .setSubject(user.getEmail())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7)) // 7 days expiry
//                .claim("role", user.getRole().name())
//                .claim("userId", user.getId())
//                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS256)
//                .compact();
//    }

    // ✅ Generate JWT for Phone Number (for OTP)
    
    public String generateToken(UserEntity user) {
        return Jwts.builder()
                .setSubject(user.getPhoneNumber())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7)) // 7 days expiry
                .claim("role", user.getRole())
                .claim("userId", user.getId())
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }
    
    
    
    public int getUserIdFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return extractUserId(authHeader.replace("Bearer ", ""));
        }
        throw new RuntimeException("Missing or invalid Authorization header");
    }
    
    
 

    
    
    
    


    // ✅ Extract Phone Number from JWT
    public static String extractPhoneNumber(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject(); // Returns phone number from claims
    }

    // ✅ Validate if Token is Valid (not expired)
    public static boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getExpiration().after(new Date()); // Check if the token is expired
        } catch (Exception e) {
            return false; // Invalid token or expired
        }
    }

    // ✅ Extract User ID from JWT Token
    public int extractUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("userId", Integer.class); // Extract userId from token
    }

    // ✅ Extract Role from JWT Token
    public String extractRole(String token) {
        Claims claims = getClaims(token);
        return claims.get("role", String.class); // Extract role from token
    }

    // ✅ Extract Username (Email) from JWT Token
//    public String extractUsername(String token) {
//        Claims claims = getClaims(token);
//        return claims.getSubject(); // Extract username (email) from token
//    }

    // ✅ Validate Token for User's Email
    public boolean validateToken(String token, String phonenumber) {
        return extractPhoneNumber(token).equals(phonenumber) && !isTokenExpired(token);
    }

    // ✅ Check if Token is Expired
    private boolean isTokenExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().before(new Date()); // Check if the token is expired
    }

    // ✅ Get Claims from Token
    private static Claims getClaims(String token) {
        token = token.trim();
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Logger
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class); // For logging

    // ✅ Helper to remove "Bearer " prefix from token if exists
    private String removeBearerPrefix(String token) {
        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }
}
