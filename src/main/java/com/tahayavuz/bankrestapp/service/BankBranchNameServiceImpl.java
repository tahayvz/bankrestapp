package com.tahayavuz.bankrestapp.service;

import com.tahayavuz.bankrestapp.domain.BranchNameDetails;
import com.tahayavuz.bankrestapp.exception.ResourceNotFoundException;
import com.tahayavuz.bankrestapp.model.BranchName;
import com.tahayavuz.bankrestapp.repository.BranchNameRepository;
import com.tahayavuz.bankrestapp.service.helper.BankingServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BankBranchNameServiceImpl implements BankBranchNameService {

    @Autowired
    private BranchNameRepository branchNameRepository;
    @Autowired
    private BankingServiceHelper bankingServiceHelper;

    @Override
    public List<BranchNameDetails> findAll() {
        List<BranchNameDetails> allBranchNameDetails = new ArrayList<>();

        Iterable<BranchName> branchNameList = branchNameRepository.findAll();

        branchNameList.forEach(branchName -> {
            allBranchNameDetails.add(bankingServiceHelper.convertToBranchNameDomain(branchName));
        });

        return allBranchNameDetails;
    }

    @Override
    public ResponseEntity<Object> findByBranchName(String branchName) {

        Optional<BranchName> branchNameEntityOptional = branchNameRepository.findByBranchName(branchName);

        if (branchNameEntityOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(bankingServiceHelper.convertToBranchNameDomain(branchNameEntityOptional.get()));
        } else {
            throw new ResourceNotFoundException("Branch Name not found. For Branch Name value: " + branchName);
        }

    }

    @Override
    public ResponseEntity<Object> addBankBranchName(BranchNameDetails branchNameDetails) {
        BranchName branchName = bankingServiceHelper.convertToBranchNameEntity(branchNameDetails);
        branchNameRepository.save(branchName);

        return ResponseEntity.status(HttpStatus.CREATED).body("New Branch Name created successfully.");
    }
}
