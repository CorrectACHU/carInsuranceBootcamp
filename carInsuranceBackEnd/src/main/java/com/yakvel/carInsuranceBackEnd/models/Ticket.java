package com.yakvel.carInsuranceBackEnd.models;


import com.yakvel.carInsuranceBackEnd.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "tickets")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime date_of_incident;

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    private Person ticket_owner;

    @ManyToOne
    @JoinColumn(name="manager_id")
    private Person current_manager;

    @ManyToOne
    @JoinColumn(name="estimator_id")
    private Person current_estimator;
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;
    private String vehicle_info;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_condition_id", referencedColumnName = "id")
    private VehicleCondition vehicle_condition;
    @OneToMany
    @JoinColumn(name = "estimated_parts_ids")
    private Set<EstimatedPart> estimated_parts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "other_charge_id", referencedColumnName = "id")
    private OtherCharge other_charge;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tickets_contacts",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private Set<Contact> other_contacts;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "ticket_id")
    private Set<Comment> comments = new HashSet<>();
}
