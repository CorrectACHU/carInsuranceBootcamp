package com.yakvel.carInsuranceBackEnd.controllers.estimator.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yakvel.carInsuranceBackEnd.enums.TicketStatus;
import com.yakvel.carInsuranceBackEnd.models.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class TicketEstimatorDto {
    private Long id;
    private TicketStatus ticketStatus;
    private Set<EstimatedPart> estimatedParts;
    private Set<Contact> otherContacts;
    private Supplement supplement;
}


