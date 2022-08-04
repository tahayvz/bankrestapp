package com.tahayavuz.bankrestapp.services;

import com.tahayavuz.bankrestapp.domains.BranchCodeDetails;
import com.tahayavuz.bankrestapp.models.BranchCode;
import com.tahayavuz.bankrestapp.repositories.BranchCodeRepository;
import com.tahayavuz.bankrestapp.services.helper.BankingServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BankBranchCodeServiceImpl  implements BankBranchCodeService{


    @Autowired
    private BranchCodeRepository branchCodeRepository;
    @Autowired
    private BankingServiceHelper bankingServiceHelper;

    @Override
    public List<BranchCodeDetails> findAll() {
        List<BranchCodeDetails> allBranchCodeDetails = new ArrayList<>();

        Iterable<BranchCode> branchCodeList = branchCodeRepository.findAll();

        branchCodeList.forEach(branchCode -> {
            allBranchCodeDetails.add(bankingServiceHelper.convertToBranchCodeDomain(branchCode));
        });

        return allBranchCodeDetails;
    }

    @Override
    public BranchCodeDetails findByBranchCode(String branchCode) {
        return null;
    }

    @Override
    public ResponseEntity<Object> addBankBranchCode(BranchCodeDetails branchCodeDetails) {
        BranchCode branchCode = bankingServiceHelper.convertToBranchCodeEntity(branchCodeDetails);
        branchCodeRepository.save(branchCode);

        return ResponseEntity.status(HttpStatus.CREATED).body("New Customer created successfully.");
    }

}
