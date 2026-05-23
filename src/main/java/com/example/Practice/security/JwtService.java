package com.example.Practice.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;

@Service
public class JwtService {

    // Secret Key
    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkey12";

    // Generate Secret Key
    private Key getSignKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );
    }

 public String generateToken(String email, String role) {

    return Jwts.builder()

            .setSubject(email)

            .claim("role", role)

            .setIssuedAt(new Date())

            .setExpiration(
                    new Date(System.currentTimeMillis()
                            + 1000 * 60 * 60)
            )

            .signWith(
                    getSignKey(),
                    SignatureAlgorithm.HS256
            )

            .compact();
}
    public String extractEmail(String token) {

    Claims claims = Jwts.parserBuilder()

            .setSigningKey(getSignKey())

            .build()

            .parseClaimsJws(token)

            .getBody();

    return claims.getSubject();
}
public boolean validateToken(String token, String email) {

    String extractedEmail = extractEmail(token);

    return extractedEmail.equals(email);
}
}