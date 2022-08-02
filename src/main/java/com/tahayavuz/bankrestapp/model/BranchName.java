package com.tahayavuz.bankrestapp.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "branch_name")
public class BranchName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BRANCH_NAME_ID")
    private Long id;

    private String branchName;

    @OneToMany(mappedBy = "customerBranchName", cascade = CascadeType.ALL)
    private Set<Customer> customers  = new HashSet<>();

    public BranchName addCustomer(Customer customer) {
        customer.setCustomerBranchName(this);
        this.customers.add(customer);
        return this;
    }
}
