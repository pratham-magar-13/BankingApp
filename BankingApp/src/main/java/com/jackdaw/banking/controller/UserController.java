package com.jackdaw.banking.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jackdaw.banking.dto.BankResponse;
import com.jackdaw.banking.dto.CreditDebitRequest;
import com.jackdaw.banking.dto.EnquiryRequest;
import com.jackdaw.banking.dto.TransferRequest;
import com.jackdaw.banking.dto.UserRequest;
import com.jackdaw.banking.entity.User;
import com.jackdaw.banking.services.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/user")
public class UserController {

    public final UserService userService;
	
	public UserController(UserService userService)
	{
		this.userService=userService;
	}
	@PostMapping
	public BankResponse createAccount(@RequestBody UserRequest userRequest)
	{
		return userService.createAccount(userRequest);
	}
	@GetMapping("balanceEnquiry")
	public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request) {
		return userService.balanceEnquiry(request);
	}
	
	@GetMapping("nameEnquiry")
	public String nameEnquiry(@RequestBody EnquiryRequest request)
	{
		return userService.nameEnquiry(request);
	}
	@PostMapping("credit")
	public BankResponse creditAccount(@RequestBody CreditDebitRequest request)
	{
		return userService.creditAccount(request);
	}
	@PostMapping("debit")
	public BankResponse debitAccount(@RequestBody CreditDebitRequest request)
	{
		return userService.debitAccount(request);
	}
	@GetMapping("allusers")
	public List<User> getAllUsers()
	{
		return userService.getAllUsers();
	}
	@PostMapping("transferFunds")
	public BankResponse transferFunds(@RequestBody TransferRequest request)
	{
		return userService.transfer(request);
	}
}
