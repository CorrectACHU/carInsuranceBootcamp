package com.yakvel.carInsuranceBackEnd.controllers.user.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yakvel.carInsuranceBackEnd.models.Address;
import com.yakvel.carInsuranceBackEnd.models.Phone;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class ContactDto {
    private String email;
    private String firstName;
    private String lastName;
    private String contactType;
    private Set<Phone> phones;
    private Set<Address> addresses;
    private String notes;

}
