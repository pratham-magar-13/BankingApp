package com.jackdaw.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {

	private String sourceAccountNumber;
	private String destinationAccountNumber;
	private int amount;
}
