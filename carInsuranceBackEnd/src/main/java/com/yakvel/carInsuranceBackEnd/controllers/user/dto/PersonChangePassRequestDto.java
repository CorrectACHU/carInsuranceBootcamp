package com.yakvel.carInsuranceBackEnd.controllers.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonChangePassRequestDto {
    private String oldPassword;
    private String newPassword;
}
