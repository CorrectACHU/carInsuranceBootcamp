package com.yakvel.carInsuranceBackEnd.config;


import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
public class JwtConfig {


    @Value("${secret.key}")
    private String SECRET_KEY;

    @Bean
    public JwtParser jwtParser() {
        return Jwts.parserBuilder().setSigningKey(secretKey()).build();
    }

    @Bean
    public Key secretKey() {
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

}
