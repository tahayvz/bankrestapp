package com.tahayavuz.bankrestapp.controllers;

import com.tahayavuz.bankrestapp.domains.CustomerDetails;
import com.tahayavuz.bankrestapp.services.BankingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
@Api(tags = { "Customer REST endpoints" })
public class CustomerController {

	public CustomerController(BankingService bankingService) {
		this.bankingService = bankingService;
	}

	@Autowired
	private final BankingService bankingService;

	@GetMapping(path = "/all")
	@ApiOperation(value = "Find all customers", notes = "Gets details of all the customers")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })

	public List<CustomerDetails> getAllCustomers() {

		return bankingService.findAll();
	}

	@PostMapping(path = "/add")
	@ApiOperation(value = "Add a Customer", notes = "Add customer and create an account")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })

	public ResponseEntity<Object> addCustomer(@RequestBody CustomerDetails customer) {

		return bankingService.addCustomer(customer);
	}

	@GetMapping(path = "/{customerNumber}")
	@ApiOperation(value = "Get customer details", notes = "Get Customer details by customer number.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = CustomerDetails.class, responseContainer = "Object"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })

	public CustomerDetails getCustomer(@PathVariable Long customerNumber) {

		return bankingService.findByCustomerNumber(customerNumber);
	}

	@PutMapping(path = "/{customerNumber}")
	@ApiOperation(value = "Update customer", notes = "Update customer and any other account information associated with him.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Object.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })

	public ResponseEntity<Object> updateCustomer(@RequestBody CustomerDetails customerDetails,
                                                 @PathVariable Long customerNumber) {

		return bankingService.updateCustomer(customerDetails, customerNumber);
	}

	@DeleteMapping(path = "/{customerNumber}/delete")
	@ApiOperation(value = "Delete customer and related accounts", notes = "Delete customer and all accounts associated with him.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Object.class),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })

	public ResponseEntity<Object> deleteCustomer(@PathVariable Long customerNumber) {

		return bankingService.deleteCustomer(customerNumber);
	}

	@GetMapping(path = "/branch-code/{branchCode}")
	@ApiOperation(value = "Find all customers from branch code", notes = "Gets customer lists of branch code")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })

	public List<CustomerDetails> getAllCustomersFromBranchCode( @PathVariable String branchCode) {

		return bankingService.findAllCustomersFromBranchCode(branchCode );
	}

	@GetMapping(path = "/branch-name/{branchName}")
	@ApiOperation(value = "Find all customers from branch name", notes = "Gets customer lists of branch name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })

	public List<CustomerDetails> getAllCustomersFromBranchName( @PathVariable String branchName) {

		return bankingService.findAllCustomersFromBranchName(branchName );
	}
}
