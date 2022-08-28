package com.ml.order.repositories;

import com.ml.order.constants.StatusOrder;
import com.ml.order.constants.TypeOrder;
import com.ml.order.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

	Page<Order> findByStatusOrder(StatusOrder status, Pageable pageable);

	Page<Order> findByTypeOrderAndStatusOrderIsNull(TypeOrder type, Pageable pageable);

}