package com.tahayavuz.bankrestapp.services;

import com.tahayavuz.bankrestapp.domains.BranchNameDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BankBranchNameService{

    public List<BranchNameDetails> findAll();

    public ResponseEntity<Object> findByBranchName(String branchName);

    public ResponseEntity<Object> addBankBranchName(BranchNameDetails branchNameDetails);
}
