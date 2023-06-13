package com.yakvel.carInsuranceBackEnd.controllers;


import com.yakvel.carInsuranceBackEnd.controllers.service.AuthService;
import com.yakvel.carInsuranceBackEnd.controllers.service.AuthenticationRequest;
import com.yakvel.carInsuranceBackEnd.controllers.service.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request
    ) throws AuthenticationException {
        authService.register(request);
        return ResponseEntity.ok("New user was created!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody AuthenticationRequest request
    ) {
        String token = authService.login(request);

        ResponseCookie cookie = getResponseCookie(token);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Set-Cookie")
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("The login was succeed");
    }

    private static ResponseCookie getResponseCookie(String token) {
        return ResponseCookie
                .from("token", token)
                .path("/")
                .maxAge(86400)
                .build();
    }


}
