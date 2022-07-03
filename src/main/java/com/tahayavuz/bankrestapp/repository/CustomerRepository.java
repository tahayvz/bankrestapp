package com.tahayavuz.bankrestapp.repository;

import com.tahayavuz.bankrestapp.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {

    public Optional<Customer> findByCustomerNumber(Long customerNumber);
    
}
