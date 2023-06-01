package com.yakvel.carInsuranceBackEnd.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${secret.key}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        return extractClaim(token).getSubject();
    }

    public Claims extractClaim(String token) {
        return extractAllClaims(token);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*3600))
                .signWith(SignatureAlgorithm.HS256, getSignKey())
                .compact();
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
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
