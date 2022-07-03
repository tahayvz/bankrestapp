package com.tahayavuz.bankrestapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="branch_name")
public class BranchName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BRANCH_NAME_ID")
    private Long id;

    private String branchName;

    @OneToMany(mappedBy = "customerBranchName", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Customer> customers= new ArrayList<>();
}
