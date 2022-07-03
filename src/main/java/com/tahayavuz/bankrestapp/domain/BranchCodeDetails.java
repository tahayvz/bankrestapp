package com.tahayavuz.bankrestapp.domain;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BranchCodeDetails {

    private String branchCode;

    private CustomerDetails customerDetails;

}
