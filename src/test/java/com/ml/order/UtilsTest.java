package com.ml.order;

import com.ml.order.dtos.OrderDTO;
import com.ml.order.entities.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UtilsTest {

	private final Page<OrderDTO> LIST_ORDERS_DTO = builderOrdersDTO();

	@Test
	public void mapEntityPageIntoDtoPageWithSuccess() {
		Page<Order> orderDTOS = Utils.mapEntityPageIntoDtoPage(LIST_ORDERS_DTO, Order.class);

		assertEquals(1, orderDTOS.getSize());
	}

	private Page<OrderDTO> builderOrdersDTO() {
		return new PageImpl<>(Collections.singletonList(
				OrderDTO.builder()
						.walletID(1L)
						.build()
		));
	}

}
