package com.yakvel.carInsuranceBackEnd.models;

import com.yakvel.carInsuranceBackEnd.enums.CompanyType;
import lombok.*;

import javax.persistence.*;

@Table(name = "companies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String companyName;
    @Enumerated(EnumType.ORDINAL)
    private CompanyType companyType;
}
