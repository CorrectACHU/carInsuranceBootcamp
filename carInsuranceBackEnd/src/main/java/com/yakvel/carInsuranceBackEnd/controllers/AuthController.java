package com.yakvel.carInsuranceBackEnd.controllers;


import com.yakvel.carInsuranceBackEnd.controllers.service.AuthDto;
import com.yakvel.carInsuranceBackEnd.controllers.service.AuthService;
import com.yakvel.carInsuranceBackEnd.controllers.service.AuthenticationRequest;
import com.yakvel.carInsuranceBackEnd.controllers.service.RegisterRequest;
import com.yakvel.carInsuranceBackEnd.models.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Log4j2
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
    public ResponseEntity<AuthDto> login(
            @RequestBody AuthenticationRequest request
    ) {
        List<String> authServiceAnswer = authService.login(request);

        String token = authServiceAnswer.get(0);
        String role = authServiceAnswer.get(1);

        ResponseCookie cookie = getResponseCookie(token);
        AuthDto dto = new AuthDto(role + " has logged in", role);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Set-Cookie")
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(dto);
    }

    private static ResponseCookie getResponseCookie(String token) {
        return ResponseCookie
                .from("token", token)
                .path("/")
                .maxAge(86400)
                .build();
    }


}
