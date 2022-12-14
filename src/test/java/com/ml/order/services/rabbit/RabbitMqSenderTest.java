package com.ml.order.services.rabbit;

import com.ml.order.dtos.OrderDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RabbitMqSenderTest {

	@InjectMocks
	private RabbitMqSender rabbitMqSender;

	@Mock
	private RabbitTemplate rabbitTemplate;

	@Spy
	private final RabbitMqSender springJunitService = new RabbitMqSender();

	@Before
	public void setUp() {
		ReflectionTestUtils.setField(springJunitService, "exchangeRegister", "register.exchange");
		ReflectionTestUtils.setField(springJunitService, "routingkeyRegister", "register.routingkey");
		ReflectionTestUtils.setField(springJunitService, "exchangeWallet", "wallet.routingkey");
		ReflectionTestUtils.setField(springJunitService, "routingkeyWallet", "wallet.routingkey");
	}

	@Test
	public void sendRegisterWithSuccess() {
		rabbitMqSender.sendRegister(OrderDTO.builder().build());

		verify(rabbitTemplate).convertAndSend(null, null, OrderDTO.builder().build());
	}

	@Test
	public void sendWalletWithSuccess() {
		rabbitMqSender.sendFinishedOrder(OrderDTO.builder().build());

		verify(rabbitTemplate).convertAndSend(null, null, OrderDTO.builder().build());
	}

}
