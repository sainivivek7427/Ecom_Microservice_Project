package com.micro.auth.config;


import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // private final String SECRET_KEY = "ghjklasdfb123";
    private static final String SECRET = "myverysecureandlongenoughsecretkey123!@#"; // >= 32 characters

    SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    private final long ACCESS_TOKEN_VALIDITY = 10 * 60 * 1000; // 10 minutes
    private final long REFRESH_TOKEN_VALIDITY = 24 * 60 * 60 * 1000; // 1 day

    public String generateAccessToken(UserDetails user) {
        String role= String.valueOf(user.getAuthorities().stream().findFirst().get());
        System.out.println("Role "+role);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
//        return createToken(user.getUsername(), ACCESS_TOKEN_VALIDITY,role);
    }

    public String generateRefreshToken(UserDetails user) {
        return createToken(user.getUsername(), REFRESH_TOKEN_VALIDITY);
    }

    private String createToken(String subject, long validity) {
        return Jwts.builder()
                .setSubject(subject)
//                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public String generateRefreshGuestToken() {
        return createToken("guest", REFRESH_TOKEN_VALIDITY);
    }

    /*Guest Token*/
    // Generate Guest Token for unauthenticated users (before login)
    public String generateGuestToken(String guestIDValue) {
        return Jwts.builder()
                .setSubject("guest")
                .claim("role", "guest")
                .claim("guestId",guestIDValue)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))  // Token valid for 24 hours
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Validate the JWT token
    public boolean validateGuestToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extract Claims from the JWT Token
    public Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
