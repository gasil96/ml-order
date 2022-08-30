package com.ml.order.configurations.handler;

import com.ml.order.exceptions.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RestExceptionHandlerTest {

	@Autowired
	private RestExceptionHandler handler;

	private ResponseEntity<Object> response;

	@Test
	public void handleAllExceptions() {
		response = handler.handleAllExceptions(new Exception(StringUtils.EMPTY));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void handleIllegalArgumentException() {
		response = handler.handleIllegalArgumentException(new IllegalArgumentException(StringUtils.EMPTY));
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void handleBusinessException() {
		response = handler.handleBusinessException(new BusinessException(StringUtils.EMPTY));
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void handleMethodArgumentNotValid() {
		MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
		HttpHeaders headers = mock(HttpHeaders.class);
		WebRequest webRequest = mock(WebRequest.class);

		response = handler.handleMethodArgumentNotValid(ex, headers, HttpStatus.BAD_REQUEST, webRequest);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

}
