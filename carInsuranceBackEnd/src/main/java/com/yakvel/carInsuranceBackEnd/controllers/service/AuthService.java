package com.yakvel.carInsuranceBackEnd.controllers.service;

import com.yakvel.carInsuranceBackEnd.jwt.JwtService;
import com.yakvel.carInsuranceBackEnd.models.Person;
import com.yakvel.carInsuranceBackEnd.repositories.PersonRepository;
import com.yakvel.carInsuranceBackEnd.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PersonRepository userRepo;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public void register(RegisterRequest request) throws AuthenticationException {
        System.out.println(request.getRole().getClass());
        var user = Person.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleRepository.findById(request.getRole()).orElseGet(null))
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

        return jwtService.generateToken(authentication.getPrincipal().toString());
    }
}
