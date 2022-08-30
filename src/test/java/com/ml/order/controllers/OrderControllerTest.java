package com.ml.order.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.order.constants.StatusOrder;
import com.ml.order.constants.TypeOrder;
import com.ml.order.dtos.OrderDTO;
import com.ml.order.services.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {

	private final Page<OrderDTO> ORDER_DTO_PAGE = builderOrderDTOList();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webAppContext;

	@MockBean
	private OrderService orderService;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = webAppContextSetup(webAppContext).build();
	}

	@Test
	public void orderEventWithSuccess() throws Exception {
		mockMvc.perform(post("/v1/orders/create-order")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(OrderDTO.builder()
								.typeOrder(TypeOrder.BUY)
								.price(BigDecimal.ZERO)
								.quantity(1L)
								.walletID(1L)
								.build())))
				.andExpect(status().isCreated());

		verify(orderService).orderEvent(any());
	}

	@Test
	public void orderEventWithoutRequiredAttribute() throws Exception {
		mockMvc.perform(post("/v1/orders/create-order")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(OrderDTO.builder()
								.typeOrder(TypeOrder.BUY)
								.quantity(1L)
								.walletID(1L)
								.build())))
				.andExpect(status().isBadRequest());

		verify(orderService, times(0)).orderEvent(any());
	}

	@Test
	public void listAllWithParamWithSuccess() throws Exception {
		when(orderService.listByStatus(any(), any())).thenReturn(ORDER_DTO_PAGE);

		mockMvc.perform(get("/v1/orders/list")
						.contentType(MediaType.APPLICATION_JSON)
						.queryParam("statusOrder", String.valueOf(StatusOrder.FINISHED)))
				.andExpect(jsonPath("$.content.[0].id").value(ORDER_DTO_PAGE.getContent().get(0).getId()))
				.andExpect(status().isOk());
	}

	@Test
	public void listAllWithoutParamWithSuccess() throws Exception {
		when(orderService.listByStatus(any(), any())).thenReturn(ORDER_DTO_PAGE);

		mockMvc.perform(get("/v1/orders/list")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content.[0].id").value(ORDER_DTO_PAGE.getContent().get(0).getId()))
				.andExpect(status().isOk());
	}

	private Page<OrderDTO> builderOrderDTOList() {
		return new PageImpl<>(Collections.singletonList(
				OrderDTO.builder()
						.id("1")
						.walletID(1L)
						.build()
		));
	}
}
