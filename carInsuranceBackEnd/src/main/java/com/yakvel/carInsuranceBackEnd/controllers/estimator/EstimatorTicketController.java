package com.yakvel.carInsuranceBackEnd.controllers.estimator;


import com.yakvel.carInsuranceBackEnd.controllers.estimator.dto.TicketEstimatorDto;
import com.yakvel.carInsuranceBackEnd.controllers.manager.dto.CommentDto;
import com.yakvel.carInsuranceBackEnd.controllers.service.TicketService;
import com.yakvel.carInsuranceBackEnd.controllers.user.dto.TicketDto;
import com.yakvel.carInsuranceBackEnd.mappers.ItemMapper;
import com.yakvel.carInsuranceBackEnd.models.Comment;
import com.yakvel.carInsuranceBackEnd.models.Contact;
import com.yakvel.carInsuranceBackEnd.models.Person;
import com.yakvel.carInsuranceBackEnd.models.Ticket;
import com.yakvel.carInsuranceBackEnd.repositories.CommentRepository;
import com.yakvel.carInsuranceBackEnd.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/estimator")
@RequiredArgsConstructor
@Log4j2
public class EstimatorTicketController {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ItemMapper<TicketDto,Ticket> ticketMapper;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ItemMapper<CommentDto, Comment> commentMapper;
    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getCurrentEstimatorTickets() {
        Person estimator = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ticket> tickets = ticketRepository.findByCurrentEstimator(estimator);

        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity getTicketDetails(@PathVariable long ticketId) {
        Person estimator = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        Optional<ResponseEntity<String>> checkAnswer = ticketService.checkTicketOwnership(estimator, ticket);
        if (checkAnswer.isPresent()) {return checkAnswer.get();}

        TicketDto ticketDto = ticketMapper.toDto(ticket);
        return ResponseEntity.ok(ticketDto);
    }

    @PutMapping("/ticket/{ticketId}")
    public ResponseEntity<String> updateTicketFromManager(@PathVariable long ticketId, @RequestBody TicketEstimatorDto dto) {
        Person estimator = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        Optional<ResponseEntity<String>> checkAnswer = ticketService.checkTicketOwnership(estimator, ticket);
        if (checkAnswer.isPresent()) {return checkAnswer.get();}

        mergeTicket(dto, ticket);
        ticketRepository.save(ticket);
        return ResponseEntity.ok("Ticket was updated!");
    }

    @PostMapping("/ticket/{ticketId}/comment")
    public ResponseEntity<String> addCommentToTicket(@PathVariable long ticketId, @RequestBody CommentDto dto) {
        Person manager = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        Optional<ResponseEntity<String>> checkAnswer = ticketService.checkTicketOwnership(manager, ticket);
        if (checkAnswer.isPresent()) {return checkAnswer.get();}

        Comment comment = prepareComment(dto, manager, ticket);
        commentRepository.save(comment);
        return ResponseEntity.ok("Comment was added!");
    }
    @DeleteMapping("/ticket/comment/{commentId}")
    public ResponseEntity<String> deleteCommentToTicket(@PathVariable long commentId) {
        Person estimator = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = commentRepository.findById(commentId).orElse(null);

        Optional<ResponseEntity<String>> checkAnswer = ticketService.checkCommentOwnership(estimator, comment);
        if (checkAnswer.isPresent()) {return checkAnswer.get();}

        commentRepository.delete(comment);
        return ResponseEntity.ok("Comment was deleted!");
    }

    private static Set<Contact> mergeContacts(TicketEstimatorDto dto, Ticket ticket) {
        Set<Contact> contacts = new HashSet<>();
        if (ticket.getOtherContacts()!=null) {contacts.addAll(ticket.getOtherContacts());}
        if (dto.getOtherContacts()!=null) {contacts.addAll(dto.getOtherContacts());}
        return contacts;
    }

    private static void mergeTicket(TicketEstimatorDto dto, Ticket ticket) {
        ticket.setSupplement(dto.getSupplement());
        ticket.setEstimatedParts(dto.getEstimatedParts());
        ticket.setOtherContacts(mergeContacts(dto, ticket));
        ticket.setTicketStatus(dto.getTicketStatus());
    }

    private Comment prepareComment(CommentDto dto, Person estimator, Ticket ticket) {
        Comment comment = commentMapper.toEntity(dto);
        comment.setCommentOwner(estimator);
        comment.setTicketId(ticket);
        comment.setCreatedDate(LocalDateTime.now());
        return comment;
    }
}
