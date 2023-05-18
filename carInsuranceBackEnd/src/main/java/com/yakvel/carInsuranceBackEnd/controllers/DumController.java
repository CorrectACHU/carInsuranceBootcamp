package com.yakvel.carInsuranceBackEnd.controllers;

import com.yakvel.carInsuranceBackEnd.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DumController {
    @Autowired
    private UserRepository userRep;


    @GetMapping("/")
    public String getUsers() {
        return "Hello World";
    }

}
