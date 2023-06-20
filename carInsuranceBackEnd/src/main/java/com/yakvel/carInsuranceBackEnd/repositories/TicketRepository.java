package com.yakvel.carInsuranceBackEnd.repositories;

import com.yakvel.carInsuranceBackEnd.models.Ticket;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.function.Function;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByInsuranceCompany(String insuranceCompany);
}
