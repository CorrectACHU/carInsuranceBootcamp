package com.yakvel.carInsuranceBackEnd;

import com.yakvel.carInsuranceBackEnd.models.User;
import com.yakvel.carInsuranceBackEnd.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarInsuranceBackEndApplication {

    private UserRepository userRep;

    public static void main(String[] args) {
        SpringApplication.run(CarInsuranceBackEndApplication.class, args);
    }

}
