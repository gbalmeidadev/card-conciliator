package com.conciliator.card_conciliator.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService{

    private final String SECRET_KEY = "my-secret-key"; //depois colocar em env

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) //1hr
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}