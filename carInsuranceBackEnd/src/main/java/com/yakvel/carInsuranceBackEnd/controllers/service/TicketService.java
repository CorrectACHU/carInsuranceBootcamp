package com.yakvel.carInsuranceBackEnd.controllers.service;

import com.yakvel.carInsuranceBackEnd.models.Comment;
import com.yakvel.carInsuranceBackEnd.models.Person;
import com.yakvel.carInsuranceBackEnd.models.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final Optional<ResponseEntity<String>> negativeResponse = Optional.of(new ResponseEntity<>("This ticket does not belong to current person", HttpStatus.FORBIDDEN));
    private final Optional<ResponseEntity<String>> positiveResponse = Optional.empty();
    private final Optional<ResponseEntity<String>> responseIfTicketDoesNotExist = Optional.of(ResponseEntity.badRequest().body("Ticket does not exist"));

    public Optional<ResponseEntity<String>> checkTicketOwnership(Person currentPerson, Ticket currentTicket) {

        if (currentTicket == null) {
            return responseIfTicketDoesNotExist;
        }
        switch (currentPerson.getRole().getRole()) {
            case "MANAGER":
                return (!currentTicket.getTicketOwner().getInsuranceCompany().equals(currentPerson.getInsuranceCompany())) ? negativeResponse : positiveResponse;
            case "ESTIMATOR":
                return (currentTicket.getCurrentEstimator().getId() != currentPerson.getId()) ? negativeResponse : positiveResponse;
            case "USER":
                return (currentTicket.getTicketOwner().getId() != currentPerson.getId()) ? negativeResponse : positiveResponse;
            default:
                return positiveResponse;
        }
    }

    public Optional<ResponseEntity<String>> checkCommentOwnership(Person person, Comment comment) {
        if (comment == null) {
            return Optional.of(ResponseEntity.badRequest().body("Comment does not exist"));
        } else if (comment.getCommentOwner().getId() != person.getId()) {
            return Optional.of(new ResponseEntity<>("This comment does not belong to current person", HttpStatus.FORBIDDEN));
        }
        return Optional.empty();
    }
}
