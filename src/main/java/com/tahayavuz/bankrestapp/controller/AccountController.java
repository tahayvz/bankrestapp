package com.tahayavuz.bankrestapp.controller;

import com.tahayavuz.bankrestapp.domain.AccountInformation;
import com.tahayavuz.bankrestapp.domain.TransactionDetails;
import com.tahayavuz.bankrestapp.domain.TransferDetails;
import com.tahayavuz.bankrestapp.service.BankingServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("accounts")
@Api(tags = { "Accounts and Transactions REST endpoints" })
public class AccountController {

	@Autowired
	private BankingServiceImpl bankingService;

	@GetMapping(path = "/{accountNumber}")
	@ApiOperation(value = "Get account details", notes = "Find account details by account number")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })

	public ResponseEntity<Object> getByAccountNumber(@PathVariable Long accountNumber) {

		return bankingService.findByAccountNumber(accountNumber);
	}

	@PostMapping(path = "/add/{customerNumber}")
	@ApiOperation(value = "Add a new account", notes = "Create an new account for existing customer.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })

	public ResponseEntity<Object> addNewAccount(@RequestBody AccountInformation accountInformation,
                                                @PathVariable Long customerNumber) {

		return bankingService.addNewAccount(accountInformation, customerNumber);
	}

	@PutMapping(path = "/transfer/{customerNumber}")
	@ApiOperation(value = "Transfer funds between accounts", notes = "Transfer funds between accounts.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Object.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })

	public ResponseEntity<Object> transferDetails(@RequestBody TransferDetails transferDetails,
                                                  @PathVariable Long customerNumber) {

		return bankingService.transferDetails(transferDetails, customerNumber);
	}

	@GetMapping(path = "/transactions/{accountNumber}")
	@ApiOperation(value = "Get all transactions", notes = "Get all Transactions by account number")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })

	public List<TransactionDetails> getTransactionByAccountNumber(@PathVariable Long accountNumber) {

		return bankingService.findTransactionsByAccountNumber(accountNumber);
	}
}
