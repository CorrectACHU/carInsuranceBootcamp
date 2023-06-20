package com.yakvel.carInsuranceBackEnd.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Table(name = "supplements")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "supplements")
    private Ticket ticketId;

    @OneToMany
    @JoinColumn(name = "estimated_parts_ids")
    private Set<EstimatedPart> estimatedParts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "other_charge_id", referencedColumnName = "id")
    private OtherCharge otherCharge;
}
