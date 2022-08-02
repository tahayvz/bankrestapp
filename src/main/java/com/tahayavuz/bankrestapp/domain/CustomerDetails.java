package com.tahayavuz.bankrestapp.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDetails {
    private Long id;

    private String firstName;
    
    private String lastName;
    
    private String middleName;
    
    private Long customerNumber;
    
    private String status;
    
    private AddressDetails customerAddress;
    
    private ContactDetails contactDetails;

//    private BranchNameDetails branchNameDetails;
//
//    private BranchCodeDetails branchCodeDetails;
//bunları dene customer get yapmak için ingredient bu şekilde sadece recipeId ile çağrılmış...    recursiın olmaz...
    private long branchNameId;

    private long branchCodeId;

}
