package com.yakvel.carInsuranceBackEnd.controllers.user.service;

import com.yakvel.carInsuranceBackEnd.models.Address;
import com.yakvel.carInsuranceBackEnd.models.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonContactRequest {
    private String firstName;
    private String lastName;
    private Set<Phone> phones;
    private Set<Address> addresses;
    private String notes;

}