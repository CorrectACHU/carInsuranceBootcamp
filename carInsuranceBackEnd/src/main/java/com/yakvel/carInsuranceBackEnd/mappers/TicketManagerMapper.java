package com.yakvel.carInsuranceBackEnd.mappers;

import com.yakvel.carInsuranceBackEnd.controllers.manager.dto.TicketManagerDto;
import com.yakvel.carInsuranceBackEnd.controllers.user.service.TicketDto;
import com.yakvel.carInsuranceBackEnd.models.Person;
import com.yakvel.carInsuranceBackEnd.models.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketManagerMapper implements ItemMapper<TicketManagerDto, Ticket>{
    @Override
    public Ticket toEntity(TicketManagerDto dto) {
        return Ticket
                .builder()
                .currentEstimator(dto.getCurrentEstimator())
                .otherCharge(dto.getOtherCharge())
                .otherContacts(dto.getOtherContacts())
                .supplements(dto.getSupplements())
                .build();
    }

    @Override
    public TicketManagerDto toDto(Ticket ticket) {
        Person ticketOwner = ticket.getTicketOwner();
        ticketOwner.setPassword("");
        return TicketManagerDto
                .builder()
                .id(ticket.getId())
                .currentManager(ticket.getCurrentManager())
                .currentEstimator(ticket.getCurrentEstimator())
                .ticketStatus(ticket.getTicketStatus())
                .otherCharge(ticket.getOtherCharge())
                .otherContacts(ticket.getOtherContacts())
                .supplements(ticket.getSupplements())
                .build();
    }
}
