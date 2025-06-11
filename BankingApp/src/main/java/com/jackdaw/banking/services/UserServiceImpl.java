package com.jackdaw.banking.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.jackdaw.banking.dto.AccountInfo;
import com.jackdaw.banking.dto.BankResponse;
import com.jackdaw.banking.dto.UserRequest;
import com.jackdaw.banking.entity.User;
import com.jackdaw.banking.repository.UserRepository;
import com.jackdaw.banking.utils.AccountUtils;

@Service
public class UserServiceImpl implements UserService{

	public final UserRepository userRepository;
	
	//bean
	public UserServiceImpl(UserRepository userRepository)
	{
		this.userRepository=userRepository;
	}
	@Override
	public BankResponse createAccount(UserRequest userRequest) {
		// saving a new user into the db
		//check if user already has an account
		
		if(userRepository.existsByEmail(userRequest.getEmail()))
		{
			BankResponse response=BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_EXISTS_CODE_)
					.responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE_)
					.accountInfo(null)
					.build();
			
			return response;
		}
		
		User newUser=User.builder()
				.firstName(userRequest.getFirstName())
				.lastName(userRequest.getLastName())
				.otherName(userRequest.getOtherName())
				.gender(userRequest.getGender())
				.address(userRequest.getAddress())
				.stateOfOrigin(userRequest.getStateOfOrigin())
				.accountNumber(AccountUtils.genericAccountNumber())
				.accountBalance(BigDecimal.ZERO)
				.email(userRequest.getEmail())
				.phoneNumber(userRequest.getPhoneNumber())
				.alternatibePhoneNumber(userRequest.getAlternatibePhoneNumber())
				.status("ACTIVE")
				.build();
		User savedUser=userRepository.save(newUser);
		
		return BankResponse.builder()
				.responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS_)
				.responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE_)
				.accountInfo(AccountInfo.builder()
						.accountBalance(savedUser.getAccountBalance())
						.accountNumber(savedUser.getAccountNumber())
						.accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
						.build())
				.build();
	}

}
