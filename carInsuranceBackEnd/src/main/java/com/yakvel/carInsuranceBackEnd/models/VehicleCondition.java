package com.yakvel.carInsuranceBackEnd.models;

import com.yakvel.carInsuranceBackEnd.enums.ImpactDirection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "vehicle_conditions")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private ImpactDirection impactDirection;
    private String photoFileNames;
}
