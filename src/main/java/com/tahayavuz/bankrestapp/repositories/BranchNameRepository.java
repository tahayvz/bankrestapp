package com.tahayavuz.bankrestapp.repositories;

import com.tahayavuz.bankrestapp.models.BranchName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchNameRepository extends CrudRepository<BranchName, Long> {
    Optional<BranchName> findByBranchName(String branchName);

}
