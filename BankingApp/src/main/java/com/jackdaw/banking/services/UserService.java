package com.jackdaw.banking.services;

import java.util.List;

import com.jackdaw.banking.dto.BankResponse;
import com.jackdaw.banking.dto.CreditDebitRequest;
import com.jackdaw.banking.dto.EnquiryRequest;
import com.jackdaw.banking.dto.TransferRequest;
import com.jackdaw.banking.dto.UserRequest;
import com.jackdaw.banking.entity.User;

public interface UserService {

	BankResponse createAccount(UserRequest userRequest);
	
	BankResponse balanceEnquiry(EnquiryRequest request);
	
	String nameEnquiry(EnquiryRequest request);
	
	BankResponse creditAccount(CreditDebitRequest request);
	
	BankResponse debitAccount(CreditDebitRequest request);
	
	BankResponse transfer(TransferRequest request);
	
	List<User>getAllUsers();
}
