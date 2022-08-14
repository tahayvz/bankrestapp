package com.tahayavuz.bankrestapp.services;

import com.tahayavuz.bankrestapp.domains.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface BankingService {

    public List<CustomerDetails> findAll();

    public List<BranchNameDetails> findAllBranchName();

    public List<CustomerDetails> findAllCustomersFromBranchName(String byBranchName);

    public List<CustomerDetails> findAllCustomersFromBranchCode(String branchCode);

    public ResponseEntity<Object> addCustomer(CustomerDetails customerDetails);

    public CustomerDetails findByCustomerNumber(Long customerNumber);

    public ResponseEntity<Object> updateCustomer(CustomerDetails customerDetails, Long customerNumber);

    public ResponseEntity<Object> deleteCustomer(Long customerNumber);

    public ResponseEntity<Object> findByAccountNumber(Long accountNumber);

    public ResponseEntity<Object> addNewAccount(AccountInformation accountInformation, Long customerNumber);

    public ResponseEntity<Object> transferDetails(TransferDetails transferDetails, Long customerNumber);

    public List<TransactionDetails> findTransactionsByAccountNumber(Long accountNumber);


}
