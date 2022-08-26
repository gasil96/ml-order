package com.ml.order.exceptions;

import java.io.Serializable;

public class BusinessException extends RuntimeException implements Serializable {

	public BusinessException(String msg) {
		super(msg);
	}

}
