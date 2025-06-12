package com.jackdaw.banking.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDebitRequest {

	private String accountNumber;
	private int amount;
	
}
