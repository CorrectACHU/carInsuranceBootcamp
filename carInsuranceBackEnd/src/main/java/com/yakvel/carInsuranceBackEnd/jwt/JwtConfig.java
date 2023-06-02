package com.yakvel.carInsuranceBackEnd.jwt;

import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.impl.DefaultJwtParserBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtParserBuilder parserBuilder() {return new DefaultJwtParserBuilder();}
}
