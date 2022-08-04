package com.tahayavuz.bankrestapp.controllers;

import com.tahayavuz.bankrestapp.domains.BranchNameDetails;
import com.tahayavuz.bankrestapp.domains.CustomerDetails;
import com.tahayavuz.bankrestapp.exceptions.GlobalExceptionHandler;
import com.tahayavuz.bankrestapp.services.BankingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BranchNameControllerTest {

    @Mock
    BankingService bankingService;

    BranchNameController branchNameController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        branchNameController = new BranchNameController(bankingService);
        mockMvc = MockMvcBuilders.standaloneSetup(branchNameController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }


    @Test
    public void testGetCustomersBranchName() throws Exception {
        BranchNameDetails branchNameDetails = new BranchNameDetails();
        branchNameDetails.setBranchName("Pendik/İstanbul");
        List<BranchNameDetails> branchNameDetailsList = new ArrayList<>();
        branchNameDetailsList.add(branchNameDetails);
        when(bankingService.findAllBranchName()).thenReturn(branchNameDetailsList);

        mockMvc.perform(get("/branch-name/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].branchName").value("Pendik/İstanbul"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllCustomer() throws Exception {
        when(bankingService.findAllCustomersFromBranchName(anyString())).thenReturn(new ArrayList<CustomerDetails>());
        String branchName = "pendik";
        Assertions.assertFalse(branchName.isEmpty());

        mockMvc.perform(get("/branch-name/" + branchName))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}