package com.yakvel.carInsuranceBackEnd.models;

import com.yakvel.carInsuranceBackEnd.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "addresses")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    private String city;
    private String state;
    private String zip;
    private String address_inline;


}
