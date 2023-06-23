package com.yakvel.carInsuranceBackEnd.controllers.manager;

import com.yakvel.carInsuranceBackEnd.controllers.manager.dto.CommentDto;
import com.yakvel.carInsuranceBackEnd.controllers.manager.dto.TicketManagerDto;
import com.yakvel.carInsuranceBackEnd.controllers.service.TicketService;
import com.yakvel.carInsuranceBackEnd.controllers.user.dto.TicketDto;
import com.yakvel.carInsuranceBackEnd.mappers.ItemMapper;
import com.yakvel.carInsuranceBackEnd.models.*;
import com.yakvel.carInsuranceBackEnd.repositories.CommentRepository;
import com.yakvel.carInsuranceBackEnd.repositories.PersonRepository;
import com.yakvel.carInsuranceBackEnd.repositories.RoleRepository;
import com.yakvel.carInsuranceBackEnd.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
@Log4j2
public class ManagerTicketController {

    @Autowired
    private ItemMapper<CommentDto, Comment> commentMapper;
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private ItemMapper<TicketDto, Ticket> ticketMapper;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TicketService ticketService;


    @GetMapping("/")
    public String getUsers() {
        return "This is manager view";
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getCurrentInsuranceTickets() {
        Person manager = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ticket> tickets = ticketRepository.findByInsuranceCompany(manager.getInsuranceCompany());

        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity getManagerTicketDetails(@PathVariable long ticketId) {
        Person manager = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        Optional<ResponseEntity<String>> checkAnswer = ticketService.checkTicketOwnership(manager, ticket);
        if (checkAnswer.isPresent()) {return checkAnswer.get();}
        TicketDto ticketDto = ticketMapper.toDto(ticket);

        return ResponseEntity.ok(ticketDto);
    }

    @PutMapping("/ticket/{ticketId}")
    public ResponseEntity<String> updateTicketFromManager(@PathVariable long ticketId, @RequestBody TicketManagerDto dto) {
        Person manager = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        Optional<ResponseEntity<String>> checkAnswer = ticketService.checkTicketOwnership(manager, ticket);
        if (checkAnswer.isPresent()) {return checkAnswer.get();}

        mergeTicket(dto, ticket,manager);
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
        Person manager = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = commentRepository.findById(commentId).orElse(null);

        Optional<ResponseEntity<String>> checkAnswer = ticketService.checkCommentOwnership(manager, comment);
        if (checkAnswer.isPresent()) {return checkAnswer.get();}

        commentRepository.delete(comment);
        return ResponseEntity.ok("Comment was deleted!");
    }


    @GetMapping("/estimators")
    public ResponseEntity getListOfAvailableEstimators() {
        Role role = roleRepository.findByRole("ESTIMATOR");
        List<Person> estimators = personRepository.findByRole(role).orElse(null);
        return (estimators!=null) ? ResponseEntity.ok(estimators) : ResponseEntity.badRequest().body("There are no estimators available at this time");
    }

    private static Set<Contact> mergeContacts(TicketManagerDto dto, Ticket ticket) {
        Set<Contact> contacts = new HashSet<Contact>();
        if (ticket.getOtherContacts()!=null) {contacts.addAll(ticket.getOtherContacts());}
        if (dto.getOtherContacts()!=null) {contacts.addAll(dto.getOtherContacts());}
        return contacts;
    }

    private static void mergeTicket(TicketManagerDto dto, Ticket ticket, Person manager) {
        ticket.setCurrentManager(manager);
        ticket.setCurrentEstimator(dto.getCurrentEstimator());
        ticket.setOtherCharge(dto.getOtherCharge());
        ticket.setOtherContacts(mergeContacts(dto, ticket));
        ticket.setTicketStatus(dto.getTicketStatus());
    }

    private Comment prepareComment(CommentDto dto, Person manager, Ticket ticket) {
        Comment comment = commentMapper.toEntity(dto);
        comment.setCommentOwner(manager);
        comment.setTicketId(ticket);
        comment.setCreatedDate(LocalDateTime.now());
        return comment;
    }
}
