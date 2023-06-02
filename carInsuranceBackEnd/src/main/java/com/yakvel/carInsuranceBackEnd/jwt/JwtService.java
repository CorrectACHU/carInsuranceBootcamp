package com.yakvel.carInsuranceBackEnd.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${secret.key}")
    private String SECRET_KEY;
    private final JwtParserBuilder jwtParserBuilder;

    public JwtService(JwtParserBuilder jwtParserBuilder) {
        this.jwtParserBuilder = jwtParserBuilder;
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
                .setExpiration(new Date(System.currentTimeMillis() + 1000*3600))
                .signWith(SignatureAlgorithm.HS256, getSignKey())
                .compact();
    }

    private Claims extractAllClaims(String token) {
        try {
            return jwtParserBuilder
                    .setSigningKey(Keys.hmacShaKeyFor(getSignKey()))
                    .build()
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

    private byte[] getSignKey() {
        return SECRET_KEY.getBytes();
    }

}
