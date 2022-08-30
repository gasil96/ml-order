package com.ml.order.controllers;

import com.ml.order.constants.StatusOrder;
import com.ml.order.dtos.OrderDTO;
import com.ml.order.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Order Management", description = "Access to manipulaton Orders BUY or SELL")
@Slf4j
@RestController
@RequestMapping
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Operation(summary = "order event")
	@PostMapping("/v1/orders/create-order")
	public ResponseEntity<Void> orderEvent(@Valid @RequestBody OrderDTO orderDTO) {
		log.info("OrderController.orderEvent - Input - orderDTO.type:{} ", orderDTO.getTypeOrder());

		orderService.orderEvent(orderDTO);

		log.debug("OrderController.orderEvent - End - orderDTO: {}}", orderDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "list events by status")
	@GetMapping("/v1/orders/list")
	public ResponseEntity<Page<OrderDTO>> listAll(@RequestParam(required = false) StatusOrder statusOrder, Pageable pageable) {
		log.info("OrderController.listAll - Input - statusOrder:{} ", statusOrder);

		Page<OrderDTO> orders = orderService.listByStatus(statusOrder, pageable);

		log.debug("OrderController.listAll - End - size: {}}", orders.getContent().size());
		return ResponseEntity.ok().body(orders);
	}

}
