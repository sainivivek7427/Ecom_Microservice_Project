package com.micro.gateway.config;

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
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getSubject();
    }

//    public void validateToken(String token) {
//        Jwts.parser()
//                .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
//                .build()
//                .parseClaimsJws(token);
//    }

    public boolean isTokenValid(String token) {
        final String username = extractUsername(token);
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }
}
