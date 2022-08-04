package com.tahayavuz.bankrestapp.domains;

import com.tahayavuz.bankrestapp.models.Customer;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BranchNameDetails {
    private Long id;

    private String branchName;

    private Set<Customer> customerDetails;

}
