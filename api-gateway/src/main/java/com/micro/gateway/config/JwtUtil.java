package com.micro.gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "myverysecureandlongenoughsecretkey123!@#"; // >= 32 characters

    SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    public String extractUsername(String token) {
        System.out.println("extract username ");
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getSubject();
    }

//    public void validateToken(String token) {
//        Jwts.parser()
//                .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
//                .build()
//                .parseClaimsJws(token);
//    }

    public Claims extractClaimsAllowExpired(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims(); // 🔥 key line
        }
    }
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);

            String role = claims.get("role", String.class);

            return role != null && !"guest".equals(role);
        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = extractAllClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException e) {
            return true; // malformed / invalid signature
        }
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean validateGuestToken(String token) {
        try {
            Claims claims = extractAllClaims(token);

            String role = claims.get("role", String.class);
            String username = claims.getSubject();

            return "guest".equals(role) && "guest".equals(username);
        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
