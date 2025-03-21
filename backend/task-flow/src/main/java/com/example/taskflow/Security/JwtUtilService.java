package com.example.taskflow.Security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.example.taskflow.Security.TokenJwtConfig.*;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import static com.example.taskflow.Security.TokenJwtConfig.JWT_TIME_VALIDITY;
import static com.example.taskflow.Security.TokenJwtConfig.SECRET_KEY;

@Service
public class JwtUtilService {

    public String genereateToken(UserDetails userDetails) throws JsonProcessingException {
        Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(userDetails.getAuthorities()))
                .build();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + JWT_TIME_VALIDITY))
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return extractClaims(Claims::getSubject, token).equals(userDetails.getUsername())
                && !extractClaims(Claims::getExpiration, token).before(new Date());
    }
    private <T> T extractClaims(Function<Claims, T> claimsResolver, String token) {
        Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
        return claimsResolver.apply(claims);
    }
    public String extractUsername(String token) {
        return extractClaims(Claims::getSubject, token);
    }
}
