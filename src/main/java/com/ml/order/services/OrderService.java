package com.ml.order.services;

import com.ml.order.constants.StatusOrder;
import com.ml.order.dtos.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

	Page<OrderDTO> listByStatus(StatusOrder status, Pageable pageable);

	void orderEvent(OrderDTO order);

}
