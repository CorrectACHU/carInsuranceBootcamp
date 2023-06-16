package com.yakvel.carInsuranceBackEnd.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "vehicles")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInformation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    String year;
    String make;
    String model;
    String licensePlate;
    String licenseState;
    LocalDateTime licenseExpiration;
    String odometerValue;
}
