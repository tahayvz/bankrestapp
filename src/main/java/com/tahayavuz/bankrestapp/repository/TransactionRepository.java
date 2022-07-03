package com.tahayavuz.bankrestapp.repository;

import com.tahayavuz.bankrestapp.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {

    public Optional<List<Transaction>> findByAccountNumber(Long accountNumber);
    
}
