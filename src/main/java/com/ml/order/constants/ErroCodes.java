package com.ml.order.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErroCodes {

	INTERNAL_SERVER_ERROR("Internal Server Error."),
	ILLEGAL_ARGUMENT_EXCEPTION("Argument not valid."),
	BUSINESS_EXCEPTION("Failure related to a business rule."),

	UF_NOT_VALID("UF Not valid."),

	FEIGN_FAILED("Failed to access recourse with feing client.");

	private final String message;
}
