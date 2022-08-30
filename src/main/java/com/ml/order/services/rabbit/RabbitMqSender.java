package com.ml.order.services.rabbit;

import com.ml.order.dtos.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMqSender {

	@Autowired
	public RabbitTemplate rabbitTemplate;

	@Value("${spring.rabbitmq.exchange}")
	private String exchange;

	@Value("${spring.rabbitmq.routingkey}")
	private String routingkey;

	public void send(OrderDTO orderDTO) {
		log.info("RabbitMqSender.send - Start");

		rabbitTemplate.convertAndSend(exchange, routingkey, orderDTO);

		log.debug("RabbitMqSender.send - End");
	}

}
