package com.jackdaw.banking.utils;

import java.time.Year;
import java.util.Random;

public class AccountUtils {

	public static final String ACCOUNT_EXISTS_CODE_= "001";
	public static final String ACCOUNT_EXISTS_MESSAGE_= "This user already has an acocunt created!";
	
	public static final String ACCOUNT_CREATION_SUCCESS_="002";
	public static final String ACCOUNT_CREATION_MESSAGE_="Account Successfully created";
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
