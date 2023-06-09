package com.yakvel.carInsuranceBackEnd.controllers.service;


import com.yakvel.carInsuranceBackEnd.models.Contact;
import com.yakvel.carInsuranceBackEnd.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private Long role;
}
