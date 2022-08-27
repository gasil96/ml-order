package com.ml.order.services;

import com.ml.order.constants.StatusOrder;
import com.ml.order.dtos.OrderDTO;
import com.ml.order.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

	Page<Order> listByStatus(StatusOrder status, Pageable pageable);

	void orderEvent(OrderDTO order);

}
