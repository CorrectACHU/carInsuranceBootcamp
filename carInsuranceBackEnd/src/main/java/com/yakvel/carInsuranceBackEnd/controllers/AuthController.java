package com.yakvel.carInsuranceBackEnd.controllers;


import com.yakvel.carInsuranceBackEnd.controllers.service.AuthService;
import com.yakvel.carInsuranceBackEnd.controllers.service.AuthenticationRequest;
import com.yakvel.carInsuranceBackEnd.controllers.service.RegisterRequest;
import com.yakvel.carInsuranceBackEnd.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
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
            @RequestBody AuthenticationRequest request,
            HttpServletResponse httpServletResponse,
            HttpServletRequest httpServletRequest
    ) {
        String token = authService.login(request);

        Cookie cookie = new Cookie( "token", token );
        cookie.setMaxAge( 86400 );
        cookie.setPath( "/" );
        httpServletResponse.addCookie( cookie );
        httpServletResponse.setHeader( HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Set-Cookie" );
        return ResponseEntity.ok("Les goooo");
    }

}
