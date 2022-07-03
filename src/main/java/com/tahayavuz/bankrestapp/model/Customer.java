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
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CUSTOMER_ID")
    private Long id;

    private String firstName;

    private String lastName;

    private String middleName;

    private Long customerNumber;

    private String status;

    @ManyToOne(cascade=CascadeType.ALL)
    private Address customerAddress;

    @OneToOne(cascade=CascadeType.ALL)
    private Contact contactDetails;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn (name="branch_code_id",referencedColumnName="BRANCH_CODE_ID",nullable=false,unique=true)
    private BranchCode customerBranchCode;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn (name="branch_name_id",referencedColumnName="BRANCH_NAME_ID",nullable=false,unique=true)
    private BranchName customerBranchName;

    @Temporal(TemporalType.TIME)
    private Date createDateTime;

    @Temporal(TemporalType.TIME)
    private Date updateDateTime;

}
