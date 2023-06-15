package com.yakvel.carInsuranceBackEnd.repositories;

import com.yakvel.carInsuranceBackEnd.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
