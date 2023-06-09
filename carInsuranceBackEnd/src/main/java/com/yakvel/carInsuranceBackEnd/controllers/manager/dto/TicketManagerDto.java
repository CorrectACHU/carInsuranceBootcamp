package com.yakvel.carInsuranceBackEnd.controllers.manager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yakvel.carInsuranceBackEnd.enums.TicketStatus;
import com.yakvel.carInsuranceBackEnd.models.Contact;
import com.yakvel.carInsuranceBackEnd.models.OtherCharge;
import com.yakvel.carInsuranceBackEnd.models.Person;
import com.yakvel.carInsuranceBackEnd.models.Supplement;
import lombok.Builder;
import lombok.Data;

import java.util.Set;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class TicketManagerDto {
    private Long id;
    private Person currentManager;
    private Person currentEstimator;
    private TicketStatus ticketStatus;
    private OtherCharge otherCharge;
    private Set<Contact> otherContacts;
    private Supplement supplement;
}


