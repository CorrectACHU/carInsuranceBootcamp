package com.yakvel.carInsuranceBackEnd.controllers;


import com.yakvel.carInsuranceBackEnd.controllers.service.AuthService;
import com.yakvel.carInsuranceBackEnd.controllers.service.AuthenticationRequest;
import com.yakvel.carInsuranceBackEnd.controllers.service.AuthenticationResponse;
import com.yakvel.carInsuranceBackEnd.controllers.service.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) throws AuthenticationException {
        return ResponseEntity.ok(authService.register(request));
    }

    @CrossOrigin("http://localhost:5173/")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}
