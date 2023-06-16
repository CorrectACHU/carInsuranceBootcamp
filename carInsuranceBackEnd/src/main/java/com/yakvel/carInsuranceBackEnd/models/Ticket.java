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
    private LocalDateTime dateOfIncident;

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    private Person ticketOwner;

    @ManyToOne
    @JoinColumn(name="manager_id")
    private Person currentManager;

    @ManyToOne
    @JoinColumn(name="estimator_id")
    private Person currentEstimator;
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;
    @ManyToOne
    @JoinColumn(name="vehicle_id")
    private VehicleInformation vehicleInfo;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_condition_id", referencedColumnName = "id")
    private VehicleCondition vehicleCondition;
    @OneToMany
    @JoinColumn(name = "estimated_parts_ids")
    private Set<EstimatedPart> estimatedParts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "other_charge_id", referencedColumnName = "id")
    private OtherCharge otherCharge;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tickets_contacts",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private Set<Contact> otherContacts;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "ticketId")
    private Set<Comment> comments = new HashSet<>();
}
