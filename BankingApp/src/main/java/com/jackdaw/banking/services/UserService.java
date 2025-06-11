package com.jackdaw.banking.services;

import com.jackdaw.banking.dto.BankResponse;
import com.jackdaw.banking.dto.UserRequest;

public interface UserService {

	BankResponse createAccount(UserRequest userRequest);
	
	
	
}
