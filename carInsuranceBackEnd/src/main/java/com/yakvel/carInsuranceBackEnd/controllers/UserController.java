package com.yakvel.carInsuranceBackEnd.controllers;

import com.yakvel.carInsuranceBackEnd.models.User;
import com.yakvel.carInsuranceBackEnd.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRep;


    @GetMapping("/")
    public String getUsers() {
        return "Hello World";
    }

}
