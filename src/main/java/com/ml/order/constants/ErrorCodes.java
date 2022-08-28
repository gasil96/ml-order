package com.ml.order.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {

	INTERNAL_SERVER_ERROR("Internal Server Error."),
	ILLEGAL_ARGUMENT_EXCEPTION("Argument not valid."),
	BUSINESS_EXCEPTION("Failure related to a business rule."),
	WALLET_NOT_FOUND_EXCEPTION("Wallet not found."),
	VALIDATION_FAILED("Validation failed"),

	FEIGN_FAILED("Failed to access recourse with feing client.");

	private final String message;
}
