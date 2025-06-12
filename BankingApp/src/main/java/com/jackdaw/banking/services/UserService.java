package com.jackdaw.banking.services;

import com.jackdaw.banking.dto.BankResponse;
import com.jackdaw.banking.dto.CreditDebitRequest;
import com.jackdaw.banking.dto.EnquiryRequest;
import com.jackdaw.banking.dto.UserRequest;

public interface UserService {

	BankResponse createAccount(UserRequest userRequest);
	
	BankResponse balanceEnquiry(EnquiryRequest request);
	
	String nameEnquiry(EnquiryRequest request);
	
	BankResponse creditAccount(CreditDebitRequest request);
	
	BankResponse debitAccount(CreditDebitRequest request);
}
