package com.yakvel.carInsuranceBackEnd.models;

import com.yakvel.carInsuranceBackEnd.enums.ImpactDirection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

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
    private ImpactDirection impact_direction;
    //TODO: text_array
    private ArrayList<String> photos;
}
