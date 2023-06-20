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
public class TicketManagerDto {
    private Long id;
    private Person currentManager;
    private Person currentEstimator;
    private TicketStatus ticketStatus;
    private OtherCharge otherCharge;
    private Set<Contact> otherContacts;
    private Set<Supplement> supplements;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getCurrentManager() {
        return currentManager;
    }

    public void setCurrentManager(Person currentManager) {
        this.currentManager = currentManager;
    }

    public Person getCurrentEstimator() {
        return currentEstimator;
    }

    public void setCurrentEstimator(Person currentEstimator) {
        this.currentEstimator = currentEstimator;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public OtherCharge getOtherCharge() {
        return otherCharge;
    }

    public void setOtherCharge(OtherCharge otherCharge) {
        this.otherCharge = otherCharge;
    }

    public Set<Contact> getOtherContacts() {
        return otherContacts;
    }

    public void setOtherContacts(Set<Contact> otherContacts) {
        this.otherContacts = otherContacts;
    }

    public Set<Supplement> getSupplements() {
        return supplements;
    }

    public void setSupplements(Set<Supplement> supplements) {
        this.supplements = supplements;
    }
}


