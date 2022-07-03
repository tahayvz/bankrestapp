package com.tahayavuz.bankrestapp.repository;

import com.tahayavuz.bankrestapp.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

	Optional<Account> findByAccountNumber(Long accountNumber);
}
