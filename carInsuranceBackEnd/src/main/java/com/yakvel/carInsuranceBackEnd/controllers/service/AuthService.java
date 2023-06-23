package com.yakvel.carInsuranceBackEnd.controllers.service;

import com.yakvel.carInsuranceBackEnd.jwt.JwtService;
import com.yakvel.carInsuranceBackEnd.models.Person;
import com.yakvel.carInsuranceBackEnd.models.Role;
import com.yakvel.carInsuranceBackEnd.repositories.PersonRepository;
import com.yakvel.carInsuranceBackEnd.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
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

    public AuthDto login(AuthenticationRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        );
        Authentication authenticate = authManager.authenticate(
                authentication
        );

        Person principal = (Person) authenticate.getPrincipal();
        String role = principal.getRole().getRole();

        return getDto(authentication, role);
    }

    private AuthDto getDto(UsernamePasswordAuthenticationToken authentication, String role) {
        return AuthDto.builder()
                .role(role)
                .token(jwtService.generateToken(authentication.getPrincipal().toString()))
                .build();
    }
}
