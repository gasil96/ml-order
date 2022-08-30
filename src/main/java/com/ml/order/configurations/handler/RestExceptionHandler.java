package com.ml.order.configurations.handler;

import com.ml.order.constants.ErrorCodes;
import com.ml.order.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
		ExceptionResponseBody body = new ExceptionResponseBody(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
				ErrorCodes.INTERNAL_SERVER_ERROR.getMessage(), ex.getMessage());

		log.error("RestExceptionHandler.handleAllExceptions ex {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
		ExceptionResponseBody body = new ExceptionResponseBody(HttpStatus.BAD_REQUEST.toString(), ErrorCodes.INTERNAL_SERVER_ERROR.getMessage(),
				ex.getMessage());

		log.error("RestExceptionHandler.handleIllegalArgumentException ex {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}

	@ExceptionHandler(BusinessException.class)
	public final ResponseEntity<Object> handleBusinessException(BusinessException ex) {
		ExceptionResponseBody body = new ExceptionResponseBody(HttpStatus.BAD_REQUEST.toString(), ErrorCodes.INTERNAL_SERVER_ERROR.getMessage(),
				ex.getMessage());

		log.error("RestExceptionHandler.handleBusinessException ex {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}


	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("RestExceptionHandler.handleBusinessException ex: {}", ex.getMessage());

		List<String> errors = new ArrayList<>();

		if (Objects.nonNull(ex.getBindingResult())) {
			final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
			fieldErrors.forEach(f -> errors.add(String.format("%s : %s", f.getField(), f.getDefaultMessage())));
		}

		ExceptionResponseBody exceptionResponse = new ExceptionResponseBody(ErrorCodes.VALIDATION_FAILED, errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

}
