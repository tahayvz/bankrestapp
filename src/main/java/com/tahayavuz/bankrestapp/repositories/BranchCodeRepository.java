package com.tahayavuz.bankrestapp.repositories;

import com.tahayavuz.bankrestapp.models.BranchCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchCodeRepository extends CrudRepository<BranchCode, Long> {
    Optional<BranchCode> findByBranchCode(String branchCode);
}