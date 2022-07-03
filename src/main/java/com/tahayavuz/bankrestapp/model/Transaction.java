package com.tahayavuz.bankrestapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TX_ID")
    private Long id;

    private Long accountNumber;

    @Temporal(TemporalType.TIME)
    private Date txDateTime;

    private String txType;

    private Double txAmount;
}
