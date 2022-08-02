package com.tahayavuz.bankrestapp.service.helper;


import com.tahayavuz.bankrestapp.domain.*;
import com.tahayavuz.bankrestapp.model.*;
import com.tahayavuz.bankrestapp.repository.BranchCodeRepository;
import com.tahayavuz.bankrestapp.repository.BranchNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class BankingServiceHelper {

    public BranchNameDetails convertToBranchNameDomain(BranchName branchName) {

        return BranchNameDetails.builder()
                .id(branchName.getId())
                .branchName(branchName.getBranchName())
                .customerDetails(branchName.getCustomers())
                .build();
    }

    public BranchName convertToBranchNameEntity(BranchNameDetails branchNameDetails) {

        return BranchName.builder()
                .id(branchNameDetails.getId())
                .branchName(branchNameDetails.getBranchName())
                .build();
    }

    public BranchCodeDetails convertToBranchCodeDomain(BranchCode branchCode) {

        return BranchCodeDetails.builder()
                .id(branchCode.getId())
                .branchCode(branchCode.getBranchCode())
                .customerDetails(branchCode.getCustomer())
                .build();
    }

    public BranchCode convertToBranchCodeEntity(BranchCodeDetails branchCodeDetails) {

        return BranchCode.builder()
                .id(branchCodeDetails.getId())
                .branchCode(branchCodeDetails.getBranchCode())
                .build();
    }

    public CustomerDetails convertToCustomerDomain(Customer customer) {

        final CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setId(customer.getId());

        BranchCodeDetails branchCodeDetails = new BranchCodeDetails();
        branchCodeDetails.setId(customer.getCustomerBranchCode().getId());
        branchCodeDetails.setBranchCode(customer.getCustomerBranchCode().getBranchCode());
        branchCodeDetails.setCustomerDetails(customer.getCustomerBranchCode().getCustomer());

        BranchNameDetails branchNameDetails = new BranchNameDetails();
        branchNameDetails.setId(customer.getCustomerBranchName().getId());
        branchNameDetails.setBranchName(customer.getCustomerBranchName().getBranchName());
        branchNameDetails.setCustomerDetails(customer.getCustomerBranchName().getCustomers());
        if (customer.getCustomerBranchCode().getId() != null) {
            customerDetails.setBranchCodeId(customer.getCustomerBranchCode().getId());
            customerDetails.setBranchNameId(customer.getCustomerBranchName().getId());
        }

        return CustomerDetails.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .middleName(customer.getMiddleName())
                .lastName(customer.getLastName())
                .customerNumber(customer.getCustomerNumber())
                .status(customer.getStatus())
                .contactDetails(convertToContactDomain(customer.getContactDetails()))
                .customerAddress(convertToAddressDomain(customer.getCustomerAddress()))
                .branchCodeId(customerDetails.getBranchCodeId())
                .branchNameId(customerDetails.getBranchNameId())
                .build();
    }

    @Autowired
    private BranchCodeRepository branchCodeRepository;
    @Autowired
    private BranchNameRepository branchNameRepository;

    public Customer convertToCustomerEntity(CustomerDetails customerDetails) {
//        Optional<BranchCode> branchCodeOptional   = branchCodeRepository.findByBranchCode(
//                customerDetails.getBranchCodeId().getBranchCode());
//        Optional<BranchName> branchNameOptional   = branchNameRepository.findByBranchName(
//                customerDetails.getCustomerBranchName().getBranchName());
        final Customer customer = new Customer();
        customer.setId(customerDetails.getId());
        if (customerDetails.getBranchCodeId() > 0) {
            BranchCode branchCode = new BranchCode();
            branchCode.setId(customerDetails.getBranchCodeId());
//            branchCode.addCustomer(customer);
//            branchCode.setBranchCode(customerDetails.get());
            customer.setCustomerBranchCode(branchCode);
//            branchCode.addCustomer(customer);
//           Set<Customer> x= branchCodeOptional.get().getCustomer();
//            branchCode.setCustomer(x);

            BranchName branchName = new BranchName();
            branchName.setId(customerDetails.getBranchNameId());
            customer.setCustomerBranchName(branchName);
//            branchName.addCustomer(customer);
//            branchName.setId(branchNameOptional.get().getId());
//            branchName.setBranchName(branchNameOptional.get().getBranchName());
//            Set<Customer> xx= branchNameOptional.get().getCustomers();

//            branchName.setCustomers(xx);

            return Customer.builder()
                    .id(customerDetails.getId())
                    .firstName(customerDetails.getFirstName())
                    .middleName(customerDetails.getMiddleName())
                    .lastName(customerDetails.getLastName())
                    .customerNumber(customerDetails.getCustomerNumber())
                    .status(customerDetails.getStatus())
                    .contactDetails(convertToContactEntity(customerDetails.getContactDetails()))
                    .customerAddress(convertToAddressEntity(customerDetails.getCustomerAddress()))
                    .customerBranchCode(customer.getCustomerBranchCode())
                    .customerBranchName(customer.getCustomerBranchName())
                    .build();
        }
        return null;
    }

    public AccountInformation convertToAccountDomain(Account account) {

        return AccountInformation.builder()
                .accountType(account.getAccountType())
                .accountBalance(account.getAccountBalance())
                .accountNumber(account.getAccountNumber())
                .accountStatus(account.getAccountStatus())
                .bankInformation(convertToBankInfoDomain(account.getBankInformation()))
                .build();
    }

    public Account convertToAccountEntity(AccountInformation accInfo) {

        return Account.builder()
                .accountType(accInfo.getAccountType())
                .accountBalance(accInfo.getAccountBalance())
                .accountNumber(accInfo.getAccountNumber())
                .accountStatus(accInfo.getAccountStatus())
                .bankInformation(convertToBankInfoEntity(accInfo.getBankInformation()))
                .build();
    }

    public AddressDetails convertToAddressDomain(Address address) {

        return AddressDetails.builder().address1(address.getAddress1())
                .address2(address.getAddress2())
                .city(address.getCity())
                .state(address.getState())
                .zip(address.getZip())
                .country(address.getCountry())
                .build();
    }

    public Address convertToAddressEntity(AddressDetails addressDetails) {

        return Address.builder().address1(addressDetails.getAddress1())
                .address2(addressDetails.getAddress2())
                .city(addressDetails.getCity())
                .state(addressDetails.getState())
                .zip(addressDetails.getZip())
                .country(addressDetails.getCountry())
                .build();
    }

    public ContactDetails convertToContactDomain(Contact contact) {

        return ContactDetails.builder()
                .emailId(contact.getEmailId())
                .homePhone(contact.getHomePhone())
                .workPhone(contact.getWorkPhone())
                .build();
    }

    public Contact convertToContactEntity(ContactDetails contactDetails) {

        return Contact.builder()
                .emailId(contactDetails.getEmailId())
                .homePhone(contactDetails.getHomePhone())
                .workPhone(contactDetails.getWorkPhone())
                .build();
    }

    public BankInformation convertToBankInfoDomain(BankInfo bankInfo) {

        return BankInformation.builder()
                .branchCode(bankInfo.getBranchCode())
                .branchName(bankInfo.getBranchName())
                .routingNumber(bankInfo.getRoutingNumber())
                .branchAddress(convertToAddressDomain(bankInfo.getBranchAddress()))
                .build();
    }

    public BankInfo convertToBankInfoEntity(BankInformation bankInformation) {

        return BankInfo.builder()
                .branchCode(bankInformation.getBranchCode())
                .branchName(bankInformation.getBranchName())
                .routingNumber(bankInformation.getRoutingNumber())
                .branchAddress(convertToAddressEntity(bankInformation.getBranchAddress()))
                .build();
    }

    public TransactionDetails convertToTransactionDomain(Transaction transaction) {

        return TransactionDetails.builder()
                .txAmount(transaction.getTxAmount())
                .txDateTime(transaction.getTxDateTime())
                .txType(transaction.getTxType())
                .accountNumber(transaction.getAccountNumber())
                .build();
    }

    public Transaction convertToTransactionEntity(TransactionDetails transactionDetails) {

        return Transaction.builder()
                .txAmount(transactionDetails.getTxAmount())
                .txDateTime(transactionDetails.getTxDateTime())
                .txType(transactionDetails.getTxType())
                .accountNumber(transactionDetails.getAccountNumber())
                .build();
    }

    public Transaction createTransaction(TransferDetails transferDetails, Long accountNumber, String txType) {

        return Transaction.builder()
                .accountNumber(accountNumber)
                .txAmount(transferDetails.getTransferAmount())
                .txType(txType)
                .txDateTime(new Date())
                .build();
    }
}
