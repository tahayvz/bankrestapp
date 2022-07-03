package com.tahayavuz.bankrestapp.repository;

import com.tahayavuz.bankrestapp.model.BranchCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchCodeRepository extends CrudRepository<BranchCode, String> {
    Optional<BranchCode> findByBranchCode(String branchCode);
}