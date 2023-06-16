package com.yakvel.carInsuranceBackEnd.controllers.user;

import com.yakvel.carInsuranceBackEnd.controllers.user.service.PhotoHandlingService;
import com.yakvel.carInsuranceBackEnd.controllers.user.service.TicketDto;
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Log4j2
public class UserTicketPagesController {
    @Autowired
    private PhotoHandlingService photoHandlingService;

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
                .filter(ticket -> {
            return ticket.getTicketOwner().getId() == person.getId();
        })
                .map(ticket -> ticketMapper.toDto(ticket))
                .collect(Collectors.toSet());
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/ticket")

    public ResponseEntity<String> createTicket(@RequestPart("ticket") TicketDto dto, @RequestPart("files") List<MultipartFile> photos) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String photoNames = photoHandlingService.photoHandling(dto, photos, person);
        Ticket ticket = prepareTicket(dto, person, photoNames);
        ticketRepository.save(ticket);
        return ResponseEntity.ok("Ticket was created!");
    }

    public Ticket prepareTicket(TicketDto dto, Person person, String photoNames) {
        Ticket ticket = ticketMapper.toEntity(dto);
        ticket.getVehicleCondition().setPhotoFileNames(photoNames);
        ticket.setTicketOwner(person);
        ticket.setTicketStatus(TicketStatus.NEW);
        return ticket;
    }
}


