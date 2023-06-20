package com.yakvel.carInsuranceBackEnd.controllers.manager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yakvel.carInsuranceBackEnd.models.Ticket;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class CommentDto {
    private Ticket ticketId;
    private String body;
    private LocalDateTime createdDate;
}
