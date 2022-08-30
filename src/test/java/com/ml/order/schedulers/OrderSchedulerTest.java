package com.ml.order.schedulers;

import com.ml.order.processors.OrderProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrderSchedulerTest {

	@InjectMocks
	private OrderScheduler orderScheduler;

	@Mock
	private OrderProcessor orderProcessor;

	@Test
	public void listenerWithSuccess() {
		orderScheduler.listener();

		verify(orderProcessor).checkMatch();
	}

}
