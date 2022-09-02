package com.ml.order.processors;

import com.ml.order.constants.TypeOrder;
import com.ml.order.dtos.OrderDTO;
import com.ml.order.exceptions.BusinessException;
import com.ml.order.services.OrderService;
import com.ml.order.services.rabbit.RabbitMqSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrderProcessorTest {

	private static final Pageable PAGEABLE = PageRequest.of(0, 1000);
	private static final String ID = "123123";
	private static final Long QUANTITY = 100L;
	private static final BigDecimal PRICE = BigDecimal.TEN;

	@InjectMocks
	private OrderProcessorImpl orderProcessor;

	@Mock
	private OrderService orderService;

	@Mock
	private RabbitMqSender rabbitMqSender;

	@Test
	public void checkMatchEqualsWithSuccess() {
		when(orderService.listByType(TypeOrder.SELL, PAGEABLE)).thenReturn(new PageImpl<>(Collections.singletonList(OrderDTO.builder()
				.id(ID).price(PRICE).quantity(QUANTITY).build())));
		when(orderService.listByType(TypeOrder.BUY, PAGEABLE)).thenReturn(new PageImpl<>(Collections.singletonList(OrderDTO.builder()
				.id(ID).price(PRICE).quantity(QUANTITY).build())));

		orderProcessor.checkMatch();

		verify(rabbitMqSender,times(2)).sendFinishedOrder(any());
		verify(orderService,times(2)).finished(any());
	}

	@Test
	public void checkMatchNotEqualsWithSuccess() {
		when(orderService.listByType(TypeOrder.SELL, PAGEABLE)).thenReturn(new PageImpl<>(Collections.singletonList(OrderDTO.builder()
				.id(ID).price(PRICE).quantity(QUANTITY).build())));
		when(orderService.listByType(TypeOrder.BUY, PAGEABLE)).thenReturn(new PageImpl<>(Collections.singletonList(OrderDTO.builder()
				.id(ID).price(PRICE).quantity(1L).build())));

		orderProcessor.checkMatch();

		verify(rabbitMqSender,times(0)).sendFinishedOrder(any());
		verify(orderService,times(0)).finished(any());
	}

	@Test(expected = BusinessException.class)
	public void checkMatchEqualsWithBusinessException() {
		when(orderService.listByType(TypeOrder.SELL, PAGEABLE)).thenReturn(new PageImpl<>(Collections.singletonList(OrderDTO.builder()
				.id(ID).price(PRICE).quantity(QUANTITY).build())));
		when(orderService.listByType(TypeOrder.BUY, PAGEABLE)).thenReturn(new PageImpl<>(Collections.singletonList(OrderDTO.builder()
				.id(ID).price(PRICE).quantity(QUANTITY).build())));

		doThrow(new BusinessException("")).when(orderService).finished(any());

		orderProcessor.checkMatch();
	}

}
