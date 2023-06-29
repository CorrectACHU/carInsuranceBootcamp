package com.yakvel.carInsuranceBackEnd.controllers.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yakvel.carInsuranceBackEnd.enums.TicketStatus;
import com.yakvel.carInsuranceBackEnd.models.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class TicketDto {
    private Long id;
    private Company insuranceCompany;
    private LocalDateTime dateOfIncident;
    private Person ticketOwner;
    private Person currentManager;
    private Person currentEstimator;
    private TicketStatus ticketStatus;
    private VehicleInformation vehicleInfo;
    private VehicleCondition vehicleCondition;
    private Set<EstimatedPart> estimatedParts;
    private OtherCharge otherCharge;
    private Set<Contact> otherContacts ;
    private Supplement supplement;
    private Set<Comment> comments;
}
