package com.yakvel.carInsuranceBackEnd.models;

import com.yakvel.carInsuranceBackEnd.enums.EstimateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "estimated_parts")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstimatedPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private EstimateType estimateType;
    private String description;
    private String laborHours;
    private String price;
    private String laborRate;
}