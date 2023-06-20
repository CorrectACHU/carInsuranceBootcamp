package com.yakvel.carInsuranceBackEnd.controllers.user;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping("/")
    public String getUsers() {
        return "This is user view";
    }

    @GetMapping("/tickets")
    public ResponseEntity<Set<TicketDto>> getUserTickets() {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Set<TicketDto> tickets = ticketRepository
                .findAll()
                .stream()
                .filter(ticket -> ticket.getTicketOwner().getId() == person.getId())
                .map(ticket -> ticketMapper.toDto(ticket))
                .collect(Collectors.toSet());
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity getTicketDetails(@PathVariable long ticketId) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket == null) {
            return ResponseEntity.badRequest().body("Ticket does not exist");
        } else if (ticket.getTicketOwner().getId() != person.getId()) {
            return new ResponseEntity<>("This ticket does not belong to current user", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/ticket")
    public ResponseEntity<String> createTicket(@RequestPart("ticket") TicketDto dto, @RequestPart("files") List<MultipartFile> photos) {
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
        if (ticket == null) {
            return ResponseEntity.badRequest().body("Ticket does not exist");
        } else if (ticket.getTicketOwner().getId() != person.getId()) {
            return new ResponseEntity<>("This ticket does not belong to current user", HttpStatus.FORBIDDEN);
        }

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
        ticket.setInsuranceCompany(user.getInsuranceCompany());
        ticket.getVehicleCondition().setPhotoFileNames(photoNames);
        ticket.setTicketOwner(user);
        ticket.setTicketStatus(TicketStatus.NEW);
        return ticket;
    }
}


