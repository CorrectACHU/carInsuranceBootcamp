package com.yakvel.carInsuranceBackEnd.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "other_charges")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtherCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String towing;
    private String storage;
}
