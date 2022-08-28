package com.ml.order.services;

import com.ml.order.constants.StatusOrder;
import com.ml.order.constants.TypeOrder;
import com.ml.order.dtos.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

	Page<OrderDTO> listByStatus(StatusOrder status, Pageable pageable);

	Page<OrderDTO> listByType(TypeOrder type, Pageable pageable);

	void finished(OrderDTO order);

	void orderEvent(OrderDTO order);

}
