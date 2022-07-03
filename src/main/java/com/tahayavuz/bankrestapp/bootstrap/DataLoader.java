package com.tahayavuz.bankrestapp.bootstrap;

import com.tahayavuz.bankrestapp.domain.BranchCodeDetails;
import com.tahayavuz.bankrestapp.domain.BranchNameDetails;
import com.tahayavuz.bankrestapp.service.BankBranchCodeService;
import com.tahayavuz.bankrestapp.service.BankBranchNameService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {
    private final BankBranchNameService bankBranchNameService;
    private final BankBranchCodeService bankBranchCodeService;

    public DataLoader(BankBranchNameService bankBranchNameService, BankBranchCodeService bankBranchCodeService) {
        this.bankBranchNameService = bankBranchNameService;
        this.bankBranchCodeService = bankBranchCodeService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = bankBranchNameService.findAll().size();

        if (count == 0 ){
            loadData();
        }
    }
    
    private void loadData() {

        BranchNameDetails branchNameDetail1 = new BranchNameDetails();
        branchNameDetail1.setBranchName("Pendik/İstanbul");
        bankBranchNameService.addBankBranchName(branchNameDetail1);

        BranchCodeDetails branchCodeDetail1 = new BranchCodeDetails();
        branchCodeDetail1.setBranchCode("1001");
        bankBranchCodeService.addBankBranchCode(branchCodeDetail1);

        BranchNameDetails branchNameDetail2 = new BranchNameDetails();
        branchNameDetail2.setBranchName("Kartal/İstanbul");
        bankBranchNameService.addBankBranchName(branchNameDetail2);

        BranchCodeDetails branchCodeDetail2 = new BranchCodeDetails();
        branchCodeDetail2.setBranchCode("1002");
        bankBranchCodeService.addBankBranchCode(branchCodeDetail2);

        BranchNameDetails branchNameDetail3 = new BranchNameDetails();
        branchNameDetail3.setBranchName("Atalar/İstanbul");
        bankBranchNameService.addBankBranchName(branchNameDetail3);

        BranchCodeDetails branchCodeDetail3 = new BranchCodeDetails();
        branchCodeDetail3.setBranchCode("1003");
        bankBranchCodeService.addBankBranchCode(branchCodeDetail3);

        BranchNameDetails branchNameDetail4 = new BranchNameDetails();
        branchNameDetail4.setBranchName("Beykoz/İstanbul");
        bankBranchNameService.addBankBranchName(branchNameDetail4);

        BranchCodeDetails branchCodeDetail4 = new BranchCodeDetails();
        branchCodeDetail4.setBranchCode("1004");
        bankBranchCodeService.addBankBranchCode(branchCodeDetail4);

        BranchNameDetails branchNameDetail5 = new BranchNameDetails();
        branchNameDetail5.setBranchName("Beşiktaş/İstanbul");
        bankBranchNameService.addBankBranchName(branchNameDetail5);

        BranchCodeDetails branchCodeDetail5 = new BranchCodeDetails();
        branchCodeDetail5.setBranchCode("1005");
        bankBranchCodeService.addBankBranchCode(branchCodeDetail5);

        System.out.println("Loaded branch....");
    }
}