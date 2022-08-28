package com.ml.order.schedulers;

import com.ml.order.processors.OrderProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@EnableScheduling
@Component
public class OrderScheduler {

	@Autowired
	private OrderProcessor orderProcessor;

	@Scheduled(cron = "${scheduled-times.start-process}", zone = "America/Sao_Paulo")
	public void listener() {
		log.debug("OrderScheduler.listener - Start");

		orderProcessor.checkMatch();

		System.err.println("Start process:" + LocalDateTime.now());
		log.debug("OrderScheduler.listener - End");
	}

}
