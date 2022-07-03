package com.tahayavuz.bankrestapp.service;

import com.tahayavuz.bankrestapp.domain.BranchNameDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BankBranchNameService{

    public List<BranchNameDetails> findAll();

    public ResponseEntity<Object> findByBranchName(String branchName);

    public ResponseEntity<Object> addBankBranchName(BranchNameDetails branchNameDetails);
}
