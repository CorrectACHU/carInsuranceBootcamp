package com.yakvel.carInsuranceBackEnd.controllers.manager;

import com.yakvel.carInsuranceBackEnd.controllers.manager.dto.CommentDto;
import com.yakvel.carInsuranceBackEnd.controllers.manager.dto.TicketManagerDto;
import com.yakvel.carInsuranceBackEnd.controllers.user.service.TicketDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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


    @GetMapping("/")
    public String getUsers() {
        return "This is manager view";
    }

    @GetMapping("/tickets")
    public ResponseEntity<Set<TicketDto>> getCurrentInsuranceTickets() {
        Person manager = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<TicketDto> tickets = ticketRepository.findAll()
                .stream()
                .filter(ticket -> ticket.getTicketOwner().getInsuranceCompany().equals(manager.getInsuranceCompany()))
                .map(ticket -> ticketMapper.toDto(ticket))
                .collect(Collectors.toSet());
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity getManagerTicketDetails(@PathVariable long ticketId) {
        Person manager = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        ResponseEntity<String> checkAnswer = checkTicket(manager, ticket);
        if (checkAnswer!=null) {return checkAnswer;}
        TicketDto ticketDto = ticketMapper.toDto(ticket);

        return ResponseEntity.ok(ticketDto);
    }

    @PutMapping("/ticket/{ticketId}")
    public ResponseEntity<String> updateTicketFromManager(@PathVariable long ticketId, @RequestBody TicketManagerDto dto) {
        Person manager = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        ResponseEntity<String> checkAnswer = checkTicket(manager, ticket);
        if (checkAnswer!=null) {return checkAnswer;}

        mergeTicket(dto, ticket,manager);
        ticketRepository.save(ticket);
        return ResponseEntity.ok("Ticket was updated!");
    }

    @PostMapping("/ticket/{ticketId}/comment")
    public ResponseEntity<String> addCommentToTicket(@PathVariable long ticketId, @RequestBody CommentDto dto) {
        Person manager = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        ResponseEntity<String> checkAnswer = checkTicket(manager, ticket);
        if (checkAnswer!=null) {return checkAnswer;}

        Comment comment = prepareComment(dto, manager, ticket);
        commentRepository.save(comment);
        return ResponseEntity.ok("Comment was added!");
    }
    @DeleteMapping("/ticket/comment/{commentId}")
    public ResponseEntity<String> deleteCommentToTicket(@PathVariable long commentId) {
        Person manager = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = commentRepository.findById(commentId).orElse(null);

        ResponseEntity<String> checkAnswer = checkComment(manager, comment);
        if (checkAnswer!=null) {return checkAnswer;}

        commentRepository.delete(comment);
        return ResponseEntity.ok("Comment was deleted!");
    }

    private static ResponseEntity<String> checkTicket(Person manager, Ticket ticket) {
        if (ticket == null) {
            return ResponseEntity.badRequest().body("Ticket does not exist");
        } else if (!ticket.getTicketOwner().getInsuranceCompany().equals(manager.getInsuranceCompany())) {
            return new ResponseEntity<>("This ticket does not belong to current manager", HttpStatus.FORBIDDEN);
        }
        return null;
    }
    private static ResponseEntity<String> checkComment(Person manager, Comment comment) {
        if (comment == null) {
            return ResponseEntity.badRequest().body("Comment does not exist");
        } else if (comment.getCommentOwner().getId() != manager.getId()) {
            return new ResponseEntity<>("This comment does not belong to current manager", HttpStatus.FORBIDDEN);
        }
        return null;
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
