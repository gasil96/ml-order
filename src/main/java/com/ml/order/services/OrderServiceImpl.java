package com.ml.order.services;

import com.ml.order.constants.StatusOrder;
import com.ml.order.dtos.OrderDTO;
import com.ml.order.entities.Order;
import com.ml.order.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Page<OrderDTO> listByStatus(StatusOrder status, Pageable pageable) {
		log.info("OrderServiceImpl.listByStatus - Input - status: {}, pageSize: {}", status, pageable.getPageSize());

		Page<Order> entities = orderRepository.findAll(pageable); //TODO: change this
		Page<OrderDTO> ordersDTO = mapEntityPageIntoDtoPage(entities, OrderDTO.class);

		log.debug("OrderServiceImpl.listByStatus - End - page:{}", ordersDTO);
		return ordersDTO;
	}

	@Override
	public void orderEvent(OrderDTO order) {
		log.info("OrderServiceImpl.orderEvent - Input - walletBuy:{}, walletSell:{}", order.getWalletBuyId(), order.getWalletSellId());
		//TODO: impl
		log.debug("OrderServiceImpl.orderEvent - End - order:{}", order);
	}

	private <D, T> Page<D> mapEntityPageIntoDtoPage(Page<T> entities, Class<D> dtoClass) {
		return entities.map(objectEntity -> mapper.map(objectEntity, dtoClass));
	}

}
