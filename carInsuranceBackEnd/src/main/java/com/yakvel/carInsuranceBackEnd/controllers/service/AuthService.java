package com.yakvel.carInsuranceBackEnd.controllers.service;

import com.yakvel.carInsuranceBackEnd.jwt.JwtService;
import com.yakvel.carInsuranceBackEnd.enums.Role;
import com.yakvel.carInsuranceBackEnd.models.User;
import com.yakvel.carInsuranceBackEnd.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public void register(RegisterRequest request) throws AuthenticationException {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        try {
            userRepo.save(user);
        } catch (Exception e) {
            String formattedString = String.format("User with email %s - is already exist", request.getEmail());
            throw new AuthenticationException(formattedString);
        }
    }

    public String login(AuthenticationRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        );
        authManager.authenticate(
                authentication
        );

        var user = userRepo.findByEmail(request.getEmail())
                .orElseThrow();
        return jwtService.generateToken(user);
    }
}
