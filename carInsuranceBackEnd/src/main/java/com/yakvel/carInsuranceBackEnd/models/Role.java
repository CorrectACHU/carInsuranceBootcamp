package com.yakvel.carInsuranceBackEnd.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String role;
}
