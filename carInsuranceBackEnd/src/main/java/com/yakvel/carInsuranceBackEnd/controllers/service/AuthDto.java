package com.yakvel.carInsuranceBackEnd.controllers.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
    private String message;
    private String role;
    private String token;
}
