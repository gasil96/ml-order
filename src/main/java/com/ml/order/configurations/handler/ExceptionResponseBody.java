package com.ml.order.configurations.handler;

import com.ml.order.constants.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public class ExceptionResponseBody implements Serializable {

	private String code;
	private String message;
	private List<String> details;

	public ExceptionResponseBody(String code, String message, String details) {
		this.code = code;
		this.message = message;
		this.details = Collections.singletonList(details);
	}

	public ExceptionResponseBody(ErrorCodes errorCode, List<String> details) {
		this.code = errorCode.name();
		this.message = errorCode.getMessage();
		this.details = Collections.unmodifiableList(details);
	}


}
