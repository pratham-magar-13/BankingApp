package com.jackdaw.banking.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.jackdaw.banking.dto.AccountInfo;
import com.jackdaw.banking.dto.BankResponse;
import com.jackdaw.banking.dto.CreditDebitRequest;
import com.jackdaw.banking.dto.EnquiryRequest;
import com.jackdaw.banking.dto.TransferRequest;
import com.jackdaw.banking.dto.UserRequest;
import com.jackdaw.banking.entity.User;
import com.jackdaw.banking.repository.UserRepository;
import com.jackdaw.banking.utils.AccountUtils;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	public final UserRepository userRepository;

	// bean
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public BankResponse createAccount(UserRequest userRequest) {
		// saving a new user into the db
		// check if user already has an account

		if (userRepository.existsByEmail(userRequest.getEmail())) {
			BankResponse response = BankResponse.builder().responseCode(AccountUtils.ACCOUNT_EXISTS_CODE_)
					.responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE_).accountInfo(null).build();

			return response;
		}

		User newUser = User.builder().firstName(userRequest.getFirstName()).lastName(userRequest.getLastName())
				.otherName(userRequest.getOtherName()).gender(userRequest.getGender()).address(userRequest.getAddress())
				.stateOfOrigin(userRequest.getStateOfOrigin()).accountNumber(AccountUtils.genericAccountNumber())
				.accountBalance(0).email(userRequest.getEmail()).phoneNumber(userRequest.getPhoneNumber())
				.alternatibePhoneNumber(userRequest.getAlternatibePhoneNumber()).status("ACTIVE").build();
		User savedUser = userRepository.save(newUser);

		return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS_)
				.responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE_)
				.accountInfo(AccountInfo.builder().accountBalance(savedUser.getAccountBalance())
						.accountNumber(savedUser.getAccountNumber()).accountName(savedUser.getFirstName() + " "
								+ savedUser.getLastName() + " " + savedUser.getOtherName())
						.build())
				.build();
	}

	@Override
	public BankResponse balanceEnquiry(EnquiryRequest request) {
		// check if the provided account number exists in the db
		boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
		if (!isAccountExist) {
			return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE).accountInfo(null).build();
		}

		User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
		return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_FOUND_CODE_STRING)
				.responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE_STRING)
				.accountInfo(AccountInfo.builder().accountBalance(foundUser.getAccountBalance())
						.accountNumber(foundUser.getAccountNumber())
						.accountName(foundUser.getFirstName() + " " + foundUser.getLastName()).build())
				.build();
	}

	@Override
	public String nameEnquiry(EnquiryRequest request) {

		boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
		if (!isAccountExist) {
			return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
		}
		User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
		return foundUser.getFirstName() + " " + foundUser.getLastName();

	}

	@Override
	public BankResponse creditAccount(CreditDebitRequest request) {
		// checking if the account exists
		boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
		if (!isAccountExist) {
			return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE).accountInfo(null).build();
		}
		User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
		// update the credit
		userToCredit.setAccountBalance(userToCredit.getAccountBalance() + request.getAmount());
		userRepository.save(userToCredit);

		return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS_STRING)
				.responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE_STRING)
				.accountInfo(AccountInfo.builder()
						.accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName())
						.accountNumber(userToCredit.getAccountNumber()).accountBalance(userToCredit.getAccountBalance())
						.build())
				.build();
	}

	@Override
	public BankResponse debitAccount(CreditDebitRequest request) {
		// check if the account exists or not
		boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
		if (!isAccountExist) {
			return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE).accountInfo(null).build();
		}

		User userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());
		if (userToDebit.getAccountBalance() < request.getAmount()) {
			return BankResponse.builder().responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE_STRING)
					.responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE_STRING)
					.accountInfo(AccountInfo.builder().accountBalance(userToDebit.getAccountBalance())
							.accountNumber(userToDebit.getAccountNumber())
							.accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName()).build())
					.build();
		}

		// debit the balance
		userToDebit.setAccountBalance(userToDebit.getAccountBalance() - request.getAmount());
		userRepository.save(userToDebit);

		return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS_STRING)
				.responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE_STRING)
				.accountInfo(AccountInfo.builder().accountNumber(userToDebit.getAccountNumber())
						.accountBalance(userToDebit.getAccountBalance())
						.accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName()).build())
				.build();
	}

	@Override
	public BankResponse transfer(TransferRequest request) {
		boolean isDestinationAccountExists = userRepository
				.existsByAccountNumber(request.getDestinationAccountNumber());
		if (!isDestinationAccountExists) {
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
					.accountInfo(null).build();
		}
		User sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
		if (request.getAmount() > sourceAccountUser.getAccountBalance()) {
			return BankResponse.builder().responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE_STRING)
					.responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE_STRING)
					.accountInfo(AccountInfo.builder().accountBalance(sourceAccountUser.getAccountBalance())
							.accountNumber(sourceAccountUser.getAccountNumber())
							.accountName(sourceAccountUser.getFirstName() + " " + sourceAccountUser.getLastName())
							.build())
					.build();
		}
		// debit in source user

		sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance() - request.getAmount());
		userRepository.save(sourceAccountUser);

		// credit in destination user
		User destinationAccountUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());
		destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance() + request.getAmount());
		userRepository.save(destinationAccountUser);

		return BankResponse.builder().responseCode(AccountUtils.TRANSFER_SUCCESS_CODE_STRING)
				.responseMessage(AccountUtils.TRANSFER_SUCCESS_MESSAGE_STRING).build();
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users;
	}

}
