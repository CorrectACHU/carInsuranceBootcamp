package com.yakvel.carInsuranceBackEnd.controllers.user.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonChangePassRequest {
    private String firstPassword;
    private String secondPassword;
}
