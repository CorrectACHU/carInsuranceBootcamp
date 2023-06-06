package com.yakvel.carInsuranceBackEnd.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Autowired
    private final Key signKey;
    private final JwtParser parserBuilder;

    public JwtService(Key signKey, JwtParser parserBuilder) {
        this.signKey = signKey;
        this.parserBuilder = parserBuilder;
    }

    public String extractUsername(String token) {
        return extractClaim(token).getSubject();
    }

    public Claims extractClaim(String token) {
        return extractAllClaims(token);
    }

    public String generateToken(String username) {
        return generateToken(new HashMap<>(), username);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            String username
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 3600))
                .signWith(signKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        try {
            return parserBuilder
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("token is expired");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("token was constructed badly");
        } catch (SignatureException e) {
            throw new RuntimeException("token is invalid");
        }
    }
}
