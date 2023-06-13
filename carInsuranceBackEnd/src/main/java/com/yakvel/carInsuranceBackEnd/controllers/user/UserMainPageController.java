package com.yakvel.carInsuranceBackEnd.controllers.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor

public class UserMainPageController {

    @GetMapping("/")
    public String getUsers() {
        return "This is user view";
    }

}
