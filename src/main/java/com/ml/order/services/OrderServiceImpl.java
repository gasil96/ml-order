package com.ml.order.services;

import com.ml.order.Utils;
import com.ml.order.constants.ErrorCodes;
import com.ml.order.constants.StatusOrder;
import com.ml.order.constants.TypeOrder;
import com.ml.order.dtos.OrderDTO;
import com.ml.order.entities.Order;
import com.ml.order.exceptions.BusinessException;
import com.ml.order.repositories.OrderRepository;
import com.ml.order.services.rabbit.RabbitMqSender;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * CORE
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private RabbitMqSender rabbitMqSender;

	@Override
	public Page<OrderDTO> listByStatus(StatusOrder status, Pageable pageable) {
		log.info("OrderServiceImpl.listByStatus - Input - status: {}, pageSize: {}", status, pageable.getPageSize());

		Page<Order> entities = orderRepository.findByStatusOrder(status, pageable);
		Page<OrderDTO> ordersDTO = Utils.mapEntityPageIntoDtoPage(entities, OrderDTO.class);

		log.debug("OrderServiceImpl.listByStatus - End - page:{}", ordersDTO);
		return ordersDTO;
	}

	@Override
	public Page<OrderDTO> listByType(TypeOrder type, Pageable pageable) {
		log.info("OrderServiceImpl.listByType - Input - type: {}, pageSize: {}", type, pageable.getPageSize());

		Page<Order> entities = orderRepository.findByTypeOrderAndStatusOrderIsNull(type, pageable);
		Page<OrderDTO> ordersDTO = Utils.mapEntityPageIntoDtoPage(entities, OrderDTO.class);

		log.debug("OrderServiceImpl.listByType - End - page:{}", ordersDTO);
		return ordersDTO;
	}

	@Override
	public void finished(OrderDTO orderDTO) {
		log.info("OrderServiceImpl.finished - Input - walletID:{}", orderDTO.getWalletID());
		orderDTO.setStatusOrder(StatusOrder.FINISHED);
		Order order = mapper.map(orderDTO, Order.class);

		orderRepository.save(order);
		registerEvent(orderDTO);

		log.debug("OrderServiceImpl.finished - End - order:{}", orderDTO);
	}

	@Override
	public void orderEvent(OrderDTO orderDTO) {
		log.info("OrderServiceImpl.orderEvent - Input - walletID:{}", orderDTO.getWalletID());
		orderDTO.setDateOp(LocalDateTime.now());
		Order order = mapper.map(orderDTO, Order.class);

		orderRepository.save(order);
		registerEvent(orderDTO);

		log.debug("OrderServiceImpl.orderEvent - End - order:{}", orderDTO);
	}

	private void registerEvent(OrderDTO orderDTO) {
		try {
			rabbitMqSender.sendRegister(orderDTO);
		} catch (Exception e) {
			log.error("OrderServiceImpl.registerEvent - Error - Failed Register");
		}
	}

}
