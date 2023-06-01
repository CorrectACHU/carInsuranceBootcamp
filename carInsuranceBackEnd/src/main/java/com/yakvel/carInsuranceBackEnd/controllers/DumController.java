package com.yakvel.carInsuranceBackEnd.controllers;

import com.yakvel.carInsuranceBackEnd.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class DumController {
    @Autowired
    private UserRepository userRep;


    @GetMapping("/")
    public String getUsers() {
        return "Hello World";
    }
}
