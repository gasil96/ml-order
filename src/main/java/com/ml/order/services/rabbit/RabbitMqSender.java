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

	@Value("${spring.rabbitmq.exchange-register}")
	private String exchangeRegister;

	@Value("${spring.rabbitmq.routingkey-register}")
	private String routingkeyRegister;

	@Value("${spring.rabbitmq.exchange-wallet}")
	private String exchangeWallet;

	@Value("${spring.rabbitmq.routingkey-wallet}")
	private String routingkeyWallet;

	public void sendRegister(OrderDTO orderDTO) {
		log.info("RabbitMqSender.send - Start");

		rabbitTemplate.convertAndSend(exchangeRegister, routingkeyRegister, orderDTO);

		log.debug("RabbitMqSender.send - End");
	}

	public void sendFinishedOrder(OrderDTO orderDTO) {
		log.info("RabbitMqSender.send - Start");

		rabbitTemplate.convertAndSend(exchangeWallet, routingkeyWallet, orderDTO);

		log.debug("RabbitMqSender.send - End");
	}

}
