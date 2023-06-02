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
import javax.servlet.http.Cookie;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
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

        Cookie responseCookie = new Cookie("token", token);
        responseCookie.setMaxAge(86400);
        responseCookie.setPath("/");
//        ResponseCookie responseHeader = ResponseCookie.from(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Set-Cookie").build();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Set-Cookie")
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body("The login was successful");
    }

}
