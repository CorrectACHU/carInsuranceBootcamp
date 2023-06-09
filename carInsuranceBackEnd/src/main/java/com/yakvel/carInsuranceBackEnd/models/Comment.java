package com.yakvel.carInsuranceBackEnd.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "comments")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    private Person comment_owner;
    @ManyToOne
    @JoinColumn(name = "comments")
    private Ticket ticket_id;
    private String body;
    private LocalDateTime createdDate;
}
