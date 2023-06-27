package com.yakvel.carInsuranceBackEnd.controllers.user;

import com.yakvel.carInsuranceBackEnd.controllers.service.TicketService;
import com.yakvel.carInsuranceBackEnd.controllers.user.dto.PersonDto;
import com.yakvel.carInsuranceBackEnd.controllers.user.service.PhotoHandlingService;
import com.yakvel.carInsuranceBackEnd.controllers.user.dto.TicketDto;
import com.yakvel.carInsuranceBackEnd.enums.TicketStatus;
import com.yakvel.carInsuranceBackEnd.mappers.ItemMapper;
import com.yakvel.carInsuranceBackEnd.models.Person;
import com.yakvel.carInsuranceBackEnd.models.Ticket;
import com.yakvel.carInsuranceBackEnd.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Log4j2
public class UserTicketController {
    @Autowired
    private PhotoHandlingService photoHandlingService;
    @Autowired
    private ItemMapper<PersonDto,Person> personMapper;
    @Autowired
    private ItemMapper<TicketDto, Ticket> ticketMapper;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getUserTickets() {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Ticket> tickets = ticketRepository.findByTicketOwner(person);

        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity getTicketDetails(@PathVariable long ticketId) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        Optional<ResponseEntity<String>> checkAnswer = ticketService.checkTicketOwnership(person, ticket);
        if (checkAnswer.isPresent()) {return checkAnswer.get();}
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/ticket")
    public ResponseEntity<String> createTicket(@RequestPart("ticket") TicketDto dto, @RequestPart("images") List<MultipartFile> photos) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LocalDateTime dateOfIncident = dto.getDateOfIncident();
        String photoNames = photoHandlingService.photoHandling(dateOfIncident, photos, person);
        Ticket ticket = prepareTicket(dto, person, photoNames);
        ticketRepository.save(ticket);
        return ResponseEntity.ok("Ticket was created!");
    }

    @DeleteMapping("/ticket/{ticketId}")
    public ResponseEntity<String> deleteTicket(@PathVariable long ticketId) throws IOException {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        Optional<ResponseEntity<String>> checkAnswer = ticketService.checkTicketOwnership(person, ticket);
        if (checkAnswer.isPresent()) {return checkAnswer.get();}

        boolean isDeleted = photoHandlingService.deletePhotos(ticket, person);
        if (isDeleted) {
            ticketRepository.delete(ticket);
            return ResponseEntity.ok("Ticket was deleted");
        } else {
            return ResponseEntity.badRequest().body("Ticket was not deleted");
        }
    }
    public Ticket prepareTicket(TicketDto dto, Person user, String photoNames) {
        Ticket ticket = ticketMapper.toEntity(dto);
        ticket.getVehicleCondition().setPhotoFileNames(photoNames);
        ticket.setTicketOwner(user);
        ticket.setInsuranceCompany(user.getCompany());
        ticket.setTicketStatus(TicketStatus.NEW);
        return ticket;
    }
}


