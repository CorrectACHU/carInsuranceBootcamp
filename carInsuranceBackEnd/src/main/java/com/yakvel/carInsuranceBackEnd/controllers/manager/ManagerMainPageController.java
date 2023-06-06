package com.yakvel.carInsuranceBackEnd.controllers.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")

public class ManagerMainPageController {


    @GetMapping("/")
    public String getUsers() {
        return "This is manager view";
    }

}
