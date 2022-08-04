package com.tahayavuz.bankrestapp.services;


import com.tahayavuz.bankrestapp.domains.*;
import com.tahayavuz.bankrestapp.exceptions.ResourceNotFoundException;
import com.tahayavuz.bankrestapp.models.*;
import com.tahayavuz.bankrestapp.repositories.*;
import com.tahayavuz.bankrestapp.services.helper.BankingServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BankingServiceImpl implements BankingService {

    @Autowired
    private BranchNameRepository branchNameRepository;
    @Autowired
    private BranchCodeRepository branchCodeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomerAccountXRefRepository custAccXRefRepository;
    @Autowired
    private BankingServiceHelper bankingServiceHelper;

    public BankingServiceImpl(CustomerRepository repository) {
        this.customerRepository = repository;
    }

    public List<BranchNameDetails> findAllBranchName() {

        List<BranchNameDetails> allBranchNameDetails = new ArrayList<>();

        Iterable<BranchName> branchNameList = branchNameRepository.findAll();

        branchNameList.forEach(branchName -> {
            allBranchNameDetails.add(bankingServiceHelper.convertToBranchNameDomain(branchName));
        });

        return allBranchNameDetails;
    }

    public List<CustomerDetails> findAllCustomersFromBranchCode(String byBranchCode) {

        List<CustomerDetails> allCustomerDetails = new ArrayList<>();

        Iterable<Customer> customerList = customerRepository.findAll();

        customerList.forEach(customer -> {
            if (customer.getCustomerBranchCode().getBranchCode().equals(byBranchCode)) {
                allCustomerDetails.add(bankingServiceHelper.convertToCustomerDomain(customer));
            } else {
                throw new ResourceNotFoundException("Customers not found. For Branch code value: " + byBranchCode);
            }
        });
        return allCustomerDetails;
    }

    public List<CustomerDetails> findAllCustomersFromBranchName(String byBranchName) {

        List<CustomerDetails> allCustomerDetails = new ArrayList<>();

        Iterable<Customer> customerList = customerRepository.findAll();

        customerList.forEach(customer -> {
            String branchName = customer.getCustomerBranchName().getBranchName();
            String s = branchName.substring(0, branchName.indexOf('/'));
            if (s.equals(byBranchName)) {
                allCustomerDetails.add(bankingServiceHelper.convertToCustomerDomain(customer));
            } else {
                throw new ResourceNotFoundException("Customers not found. For Branch name value: " + byBranchName);
            }
        });
        return allCustomerDetails;
    }

    public List<CustomerDetails> findAll() {

        List<CustomerDetails> allCustomerDetails = new ArrayList<>();

        Iterable<Customer> customerList = customerRepository.findAll();

        customerList.forEach(customer -> {
            allCustomerDetails.add(bankingServiceHelper.convertToCustomerDomain(customer));
        });

        return allCustomerDetails;
    }

    public ResponseEntity<Object> addCustomer(CustomerDetails customerDetails) {

//        Optional<BranchCode> branchCodeOptional = branchCodeRepository.findById(customerDetails.getBranchCodeDetails().getId());
        Optional<BranchCode> branchCodeOptional = branchCodeRepository.findById(customerDetails.getBranchCodeId());
        Optional<BranchName> branchNameOptional = branchNameRepository.findById(customerDetails.getBranchNameId());

        if (!branchCodeOptional.isPresent()) {
            System.out.println("branchcode not found for: " + branchCodeOptional);
        } else {
            BranchCode branchCode = branchCodeOptional.get();
            Optional<Customer> customerOptional = branchCode
                    .getCustomer()
                    .stream()
                    .filter(customer -> customer.getId().equals(customerDetails.getId()))
                    .findFirst();

            BranchName branchName = branchNameOptional.get();
            Optional<Customer> customerOptional2 = branchName
                    .getCustomers()
                    .stream()
                    .filter(customer -> customer.getId().equals(customerDetails.getId()))
                    .findFirst();

            if (customerOptional.isPresent()) {
                //update
                Customer customerFound = customerOptional.get();
                Address address = new Address();
                address.setAddress1(customerDetails.getCustomerAddress().getAddress1());
                address.setAddress2(customerDetails.getCustomerAddress().getAddress2());
                address.setCity(customerDetails.getCustomerAddress().getCity());
                address.setCountry(customerDetails.getCustomerAddress().getCountry());
                address.setState(customerDetails.getCustomerAddress().getState());
                address.setZip(customerDetails.getCustomerAddress().getZip());
                customerFound.setCustomerAddress(address);
            } else {
                //add new customer
                Customer customer = bankingServiceHelper.convertToCustomerEntity(customerDetails);
                customer.setCreateDateTime(new Date());
                branchCode.addCustomer(customer);
                branchName.addCustomer(customer);

                customerRepository.save(customer);
            }
            BranchCode savedBranchCode = branchCodeRepository.save(branchCode);
            BranchName savedBranchName = branchNameRepository.save(branchName);

            Optional<Customer> savedCustomerOptional = savedBranchCode.getCustomer().stream()
                    .filter(branchCodeCustomers -> branchCodeCustomers.getId().equals(customerDetails.getId()))
                    .findFirst();

//            if(!savedCustomerOptional.isPresent()){
//                //not totally safe... But best guess
//                savedCustomerOptional = savedBranchCode.getCustomer().stream()
//                        .filter(branchCodeCustomers -> branchCodeCustomers.getFirstName().equals(customerDetails.getFirstName()))
//                        .filter(branchCodeCustomers -> branchCodeCustomers.getLastName().equals(customerDetails.getLastName()))
//                        .filter(branchCodeCustomers -> branchCodeCustomers.getCustomerBranchCode().getId().equals(customerDetails.getBranchCodeId()))
//                        .findFirst();
//            }

//            Customer customer = bankingServiceHelper.convertToCustomerEntity(customerDetails);
//            customer.setCreateDateTime(new Date());
//            customerRepository.save(customer);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("New Customer created successfully.");
    }

    public CustomerDetails findByCustomerNumber(Long customerNumber) {

        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

        if (!customerEntityOpt.isPresent()) {
            throw new ResourceNotFoundException("Customer not found. For Customer Number value: " + customerNumber.toString());
        }

        return bankingServiceHelper.convertToCustomerDomain(customerEntityOpt.get());
    }

    public ResponseEntity<Object> updateCustomer(CustomerDetails customerDetails, Long customerNumber) {
        Optional<Customer> managedCustomerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
        Customer unmanagedCustomerEntity = bankingServiceHelper.convertToCustomerEntity(customerDetails);
        if (managedCustomerEntityOpt.isPresent()) {
            Customer managedCustomerEntity = managedCustomerEntityOpt.get();

            if (Optional.ofNullable(unmanagedCustomerEntity.getContactDetails()).isPresent()) {

                Contact managedContact = managedCustomerEntity.getContactDetails();
                if (managedContact != null) {
                    managedContact.setEmailId(unmanagedCustomerEntity.getContactDetails().getEmailId());
                    managedContact.setHomePhone(unmanagedCustomerEntity.getContactDetails().getHomePhone());
                    managedContact.setWorkPhone(unmanagedCustomerEntity.getContactDetails().getWorkPhone());
                } else managedCustomerEntity.setContactDetails(unmanagedCustomerEntity.getContactDetails());
            }

            if (Optional.ofNullable(unmanagedCustomerEntity.getCustomerAddress()).isPresent()) {

                Address managedAddress = managedCustomerEntity.getCustomerAddress();
                if (managedAddress != null) {
                    managedAddress.setAddress1(unmanagedCustomerEntity.getCustomerAddress().getAddress1());
                    managedAddress.setAddress2(unmanagedCustomerEntity.getCustomerAddress().getAddress2());
                    managedAddress.setCity(unmanagedCustomerEntity.getCustomerAddress().getCity());
                    managedAddress.setState(unmanagedCustomerEntity.getCustomerAddress().getState());
                    managedAddress.setZip(unmanagedCustomerEntity.getCustomerAddress().getZip());
                    managedAddress.setCountry(unmanagedCustomerEntity.getCustomerAddress().getCountry());
                } else managedCustomerEntity.setCustomerAddress(unmanagedCustomerEntity.getCustomerAddress());
            }

            managedCustomerEntity.setUpdateDateTime(new Date());
            managedCustomerEntity.setStatus(unmanagedCustomerEntity.getStatus());
            managedCustomerEntity.setFirstName(unmanagedCustomerEntity.getFirstName());
            managedCustomerEntity.setMiddleName(unmanagedCustomerEntity.getMiddleName());
            managedCustomerEntity.setLastName(unmanagedCustomerEntity.getLastName());
            managedCustomerEntity.setUpdateDateTime(new Date());

            customerRepository.save(managedCustomerEntity);

            return ResponseEntity.status(HttpStatus.OK).body("Success: Customer updated.");
        } else {
            throw new ResourceNotFoundException("Customer not found. For Customer Number value: " + customerNumber.toString());
        }
    }

    public ResponseEntity<Object> deleteCustomer(Long customerNumber) {

        Optional<Customer> managedCustomerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

        if (managedCustomerEntityOpt.isPresent()) {
            Customer managedCustomerEntity = managedCustomerEntityOpt.get();
            customerRepository.delete(managedCustomerEntity);
            return ResponseEntity.status(HttpStatus.OK).body("Success: Customer deleted.");
        } else {
            throw new ResourceNotFoundException("Customer not found. For Customer Number value: " + customerNumber.toString());
        }

        //TODO: Delete all customer entries from CustomerAccountXRef
    }

    public ResponseEntity<Object> findByAccountNumber(Long accountNumber) {

        Optional<Account> accountEntityOpt = accountRepository.findByAccountNumber(accountNumber);

        if (accountEntityOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(bankingServiceHelper.convertToAccountDomain(accountEntityOpt.get()));
        } else {
            throw new ResourceNotFoundException("Account not found. For Account Number value: " + accountNumber.toString());
        }

    }

    public ResponseEntity<Object> addNewAccount(AccountInformation accountInformation, Long customerNumber) {

        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

        if (customerEntityOpt.isPresent()) {
            Account account = bankingServiceHelper.convertToAccountEntity(accountInformation);
            account.setCreateDateTime(new Date());
            accountRepository.save(account);

            // Add an entry to the CustomerAccountXRef
            custAccXRefRepository.save(CustomerAccountXRef.builder().accountNumber(accountInformation.getAccountNumber()).customerNumber(customerNumber).build());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("New Account created successfully.");
    }

    public ResponseEntity<Object> transferDetails(TransferDetails transferDetails, Long customerNumber) {

        List<Account> accountEntities = new ArrayList<>();
        Account fromAccountEntity = null;
        Account toAccountEntity = null;

        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

        if (customerEntityOpt.isPresent()) {

            Optional<Account> fromAccountEntityOpt = accountRepository.findByAccountNumber(transferDetails.getFromAccountNumber());
            if (fromAccountEntityOpt.isPresent()) {
                fromAccountEntity = fromAccountEntityOpt.get();
            } else {
                throw new ResourceNotFoundException("From Account not found. For Account Number value: " + transferDetails.getFromAccountNumber());

            }


            Optional<Account> toAccountEntityOpt = accountRepository.findByAccountNumber(transferDetails.getToAccountNumber());
            if (toAccountEntityOpt.isPresent()) {
                toAccountEntity = toAccountEntityOpt.get();
            } else {
                throw new ResourceNotFoundException("To Account not found. For Account Number value: " + transferDetails.getToAccountNumber());
            }


            if (fromAccountEntity.getAccountBalance() < transferDetails.getTransferAmount()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient Funds.");
            } else {
                synchronized (this) {
                    fromAccountEntity.setAccountBalance(fromAccountEntity.getAccountBalance() - transferDetails.getTransferAmount());
                    fromAccountEntity.setUpdateDateTime(new Date());
                    accountEntities.add(fromAccountEntity);

                    toAccountEntity.setAccountBalance(toAccountEntity.getAccountBalance() + transferDetails.getTransferAmount());
                    toAccountEntity.setUpdateDateTime(new Date());
                    accountEntities.add(toAccountEntity);

                    accountRepository.saveAll(accountEntities);

                    Transaction fromTransaction = bankingServiceHelper.createTransaction(transferDetails, fromAccountEntity.getAccountNumber(), "DEBIT");
                    transactionRepository.save(fromTransaction);

                    Transaction toTransaction = bankingServiceHelper.createTransaction(transferDetails, toAccountEntity.getAccountNumber(), "CREDIT");
                    transactionRepository.save(toTransaction);
                }

                return ResponseEntity.status(HttpStatus.OK).body("Success: Amount transferred for Customer Number " + customerNumber);
            }

        } else {
            throw new ResourceNotFoundException("Customer not found. For Customer Number value: " + customerNumber.toString());
        }

    }


    public List<TransactionDetails> findTransactionsByAccountNumber(Long accountNumber) {
        List<TransactionDetails> transactionDetails = new ArrayList<>();
        Optional<Account> accountEntityOpt = accountRepository.findByAccountNumber(accountNumber);
        if (accountEntityOpt.isPresent()) {
            Optional<List<Transaction>> transactionEntitiesOpt = transactionRepository.findByAccountNumber(accountNumber);
            if (transactionEntitiesOpt.isPresent()) {
                transactionEntitiesOpt.get().forEach(transaction -> {
                    transactionDetails.add(bankingServiceHelper.convertToTransactionDomain(transaction));
                });
            }
        }

        return transactionDetails;
    }


}
