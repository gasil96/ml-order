package com.ml.order.services;

import com.ml.order.constants.StatusOrder;
import com.ml.order.constants.TypeOrder;
import com.ml.order.dtos.OrderDTO;
import com.ml.order.entities.Order;
import com.ml.order.exceptions.BusinessException;
import com.ml.order.repositories.OrderRepository;
import com.ml.order.services.rabbit.RabbitMqSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrderServiceTest {

	private static final LocalDateTime DATE_OP = LocalDateTime.of(1996, 11, 11, 17, 05);
	private static final String ID_STR = "112312";
	private static final Long ID = 1L;
	private static final BigDecimal PRICE = BigDecimal.TEN;
	private static final Long QUANTITY = 100L;
	private static final Pageable PAGEABLE = PageRequest.of(0, 100);
	private final OrderDTO ORDER_DTO_MOCK = builderOrderDTO();
	private final Order ORDER_MOCK = builderOrder();

	@InjectMocks
	private OrderServiceImpl orderService;

	@Mock
	private ModelMapper mapper;

	@Mock
	private RabbitMqSender rabbitMqSender;

	@Mock
	private OrderRepository orderRepository;

	@Test
	public void listByStatusWithSuccess() {
		when(orderRepository.findByStatusOrder(null, PAGEABLE)).thenReturn(new PageImpl<>(Collections.singletonList(ORDER_MOCK)));

		Page<OrderDTO> orderDTOPage = orderService.listByStatus(null, PAGEABLE);

		assertEquals(1, orderDTOPage.getSize());
	}

	@Test
	public void listByTypeWithSuccess() {
		when(orderRepository.findByTypeOrderAndStatusOrderIsNull(TypeOrder.BUY, PAGEABLE)).thenReturn(new PageImpl<>(Collections.singletonList(ORDER_MOCK)));

		Page<OrderDTO> orderDTOPage = orderService.listByType(TypeOrder.BUY, PAGEABLE);

		assertEquals(1, orderDTOPage.getSize());
	}

	@Test
	public void finishedWithSuccess() {
		when(mapper.map(ORDER_DTO_MOCK, Order.class)).thenReturn(ORDER_MOCK);

		orderService.finished(ORDER_DTO_MOCK);

		verify(orderRepository).save(ORDER_MOCK);
		verify(rabbitMqSender).sendRegister(ORDER_DTO_MOCK);
	}

	@Test
	public void orderEventWithSuccess() {
		when(mapper.map(ORDER_DTO_MOCK, Order.class)).thenReturn(ORDER_MOCK);

		orderService.orderEvent(ORDER_DTO_MOCK);

		verify(orderRepository).save(ORDER_MOCK);
		verify(rabbitMqSender).sendRegister(ORDER_DTO_MOCK);
	}

	private OrderDTO builderOrderDTO() {
		return OrderDTO.builder()
				.id(ID_STR)
				.walletID(ID)
				.typeOrder(TypeOrder.BUY)
				.price(PRICE)
				.quantity(QUANTITY)
				.statusOrder(StatusOrder.FINISHED)
				.dateOp(DATE_OP)
				.build();
	}

	private Order builderOrder() {
		return Order.builder()
				.id(ID_STR)
				.walletID(ID)
				.typeOrder(TypeOrder.BUY)
				.price(PRICE)
				.quantity(QUANTITY)
				.statusOrder(StatusOrder.FINISHED)
				.dateOp(DATE_OP)
				.build();
	}


}
