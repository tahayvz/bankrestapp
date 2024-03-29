package com.tahayavuz.bankrestapp.controllers;

import com.tahayavuz.bankrestapp.domains.BranchNameDetails;
import com.tahayavuz.bankrestapp.domains.CustomerDetails;
import com.tahayavuz.bankrestapp.services.BankingService;
import com.tahayavuz.bankrestapp.services.BankingServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("branch-name")
@Api(tags = { "Branch name REST endpoints" })
public class BranchNameController {

	public BranchNameController(BankingService bankingService) {
		this.bankingService = bankingService;
	}

	@Autowired
	private final BankingService bankingService;

	@GetMapping(path = "/all")
	@ApiOperation(value = "Find all branch names", notes = "Gets list of all the branch names")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })

	public List<BranchNameDetails> getCustomersBranchName() {

		return bankingService.findAllBranchName();
	}

	@GetMapping(path = "/{branchName}")
	@ApiOperation(value = "Find all customers", notes = "Gets list of all the customers")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public List<CustomerDetails> getAllCustomer(@PathVariable String branchName) {

		return bankingService.findAllCustomersFromBranchName(branchName);
	}
}
