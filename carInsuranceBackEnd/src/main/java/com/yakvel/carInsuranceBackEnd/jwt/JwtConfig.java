package com.yakvel.carInsuranceBackEnd.jwt;


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
@RequiredArgsConstructor
public class JwtConfig {


    @Value("${secret.key}")
    private String SECRET_KEY;

    @Bean
    public JwtParser parserBuilder() {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build();
    }

    @Bean
    public Key getSignKey() {
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

}
