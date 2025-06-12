package com.jackdaw.banking.utils;

import java.time.Year;
import java.util.Random;

public class AccountUtils {

	public static final String ACCOUNT_EXISTS_CODE_= "001";
	public static final String ACCOUNT_EXISTS_MESSAGE_= "This user already has an acocunt created!";
	
	public static final String ACCOUNT_CREATION_SUCCESS_="002";
	public static final String ACCOUNT_CREATION_MESSAGE_="Account Successfully created";
	
	public static final String ACCOUNT_NOT_EXIST_CODE="003";
	public static final String ACCOUNT_NOT_EXIST_MESSAGE="User with provided account Number doesnot exist.";
	
	public static final String ACCOUNT_FOUND_CODE_STRING="004";
	public static final String ACCOUNT_FOUND_MESSAGE_STRING="User Account Found";
	
	public static final String ACCOUNT_CREDITED_SUCCESS_STRING="005";
	public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE_STRING="User Account Credited Successfully";
	
	public static final String ACCOUNT_DEBITED_SUCCESS_STRING="006";
	public static final String ACCOUNT_DEBITED_SUCCESS_MESSAGE_STRING="User Account Debited Successfully";
	
	public static String genericAccountNumber()
	{
		//beginnning with 2025 + randomsixdigits
		
		Random random=new Random();
		Year currentYear=Year.now();
		int min=100000;
		int max=999999;
		
		//generate a random number between min and max
		
		int randNumber=random.nextInt()*(max-min + 1) + min;
		
		//convert current and randomNumber to strings, then concatenate
		
		String year =String.valueOf(currentYear);
		
		String randomNumber=String.valueOf(randNumber);
		
		StringBuilder accountNumber=new StringBuilder();
		
		return accountNumber.append(year).append(randomNumber).toString();
	}
}
