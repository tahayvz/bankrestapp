package com.tahayavuz.bankrestapp.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "branch_code")
public class BranchCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BRANCH_CODE_ID")
    private Long id;

    private String branchCode;

    @OneToMany(mappedBy = "customerBranchCode", cascade = CascadeType.ALL)
    private Set<Customer> customer  = new HashSet<>();

    public BranchCode addCustomer(Customer customer) {
        customer.setCustomerBranchCode(this);
        this.customer.add(customer);
        return this;
    }
}
