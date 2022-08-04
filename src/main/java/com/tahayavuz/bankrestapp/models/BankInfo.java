package com.tahayavuz.bankrestapp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class BankInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BANK_ID")
    private Long id;

    private String branchName;

    private Integer branchCode;

    @OneToOne(cascade=CascadeType.ALL)
    private Address branchAddress;

    private Integer routingNumber;

}
