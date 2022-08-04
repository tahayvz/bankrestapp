package com.tahayavuz.bankrestapp.services;

import com.tahayavuz.bankrestapp.domains.BranchCodeDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BankBranchCodeService {

    List<BranchCodeDetails> findAll();

    BranchCodeDetails findByBranchCode(String branchCode);

    ResponseEntity<Object> addBankBranchCode(BranchCodeDetails branchCodeDetails);

}
