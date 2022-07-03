package com.tahayavuz.bankrestapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="branch_code")
public class BranchCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BRANCH_CODE_ID")
    private Long id;

    private String branchCode;

    @OneToMany(mappedBy = "customerBranchCode", cascade = CascadeType.ALL)
    private List<Customer> customer;
}
