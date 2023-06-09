package com.yakvel.carInsuranceBackEnd.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Table(name = "contacts")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String email;
    @OneToMany
    @JoinColumn(name = "phones_ids")
    private Set<Phone> phones;
    @OneToMany
    @JoinColumn(name = "addresses_ids")
    private Set<Address> addresses;
    private String notes;


    @ManyToMany(mappedBy = "other_contacts")
    private Set<Ticket> tickets;

}
