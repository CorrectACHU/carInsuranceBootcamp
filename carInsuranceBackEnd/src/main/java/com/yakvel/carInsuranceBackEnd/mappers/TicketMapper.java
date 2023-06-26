package com.yakvel.carInsuranceBackEnd.mappers;

import com.yakvel.carInsuranceBackEnd.controllers.user.dto.TicketDto;
import com.yakvel.carInsuranceBackEnd.models.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper implements ItemMapper<TicketDto, Ticket>{
    @Override
    public Ticket toEntity(TicketDto ticketDto) {
        return Ticket
                .builder()
                .dateOfIncident(ticketDto.getDateOfIncident())
                .vehicleInfo(ticketDto.getVehicleInfo())
                .vehicleCondition(ticketDto.getVehicleCondition())
                .estimatedParts(ticketDto.getEstimatedParts())
                .otherCharge(ticketDto.getOtherCharge())
                .otherContacts(ticketDto.getOtherContacts())
                .supplement(ticketDto.getSupplement())
                .build();
    }

    @Override
    public TicketDto toDto(Ticket ticket) {
        return TicketDto
                .builder()
                .id(ticket.getId())
                .company(ticket.getInsuranceCompany())
                .dateOfIncident(ticket.getDateOfIncident())
                .ticketOwner(ticket.getTicketOwner())
                .currentManager(ticket.getCurrentManager())
                .currentEstimator(ticket.getCurrentEstimator())
                .ticketStatus(ticket.getTicketStatus())
                .vehicleInfo(ticket.getVehicleInfo())
                .vehicleCondition(ticket.getVehicleCondition())
                .estimatedParts(ticket.getEstimatedParts())
                .otherCharge(ticket.getOtherCharge())
                .otherContacts(ticket.getOtherContacts())
                .supplement(ticket.getSupplement())
                .comments(ticket.getComments())
                .build();
    }
}
