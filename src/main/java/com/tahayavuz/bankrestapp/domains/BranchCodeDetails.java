package com.tahayavuz.bankrestapp.domains;
import com.tahayavuz.bankrestapp.models.Customer;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BranchCodeDetails {
    private Long id;

    private String branchCode;

    private Set<Customer>  customerDetails;

}
