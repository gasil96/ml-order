package com.ml.order.processors;

import com.ml.order.constants.ErrorCodes;
import com.ml.order.constants.TypeOrder;
import com.ml.order.dtos.OrderDTO;
import com.ml.order.exceptions.BusinessException;
import com.ml.order.services.OrderService;
import com.ml.order.services.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class OrderProcessorImpl implements OrderProcessor {

	@Autowired
	private OrderService orderService;

	@Autowired
	private WalletService walletService;

	@Override
	public void checkMatch() {
		log.info("OrderProcessorImpl.checkMatch - Start");
		Pageable pageable = PageRequest.of(0, 1000);
		List<OrderDTO> ordersBUY = orderService.listByType(TypeOrder.BUY, pageable).getContent();
		List<OrderDTO> ordersSELL = orderService.listByType(TypeOrder.SELL, pageable).getContent();

		/**
		 * The condition for a 'match' is that the buy order has the same values as the sell order
		 * */
		ordersSELL.forEach(sell -> ordersBUY.forEach(buy -> {
			if (Objects.equals(sell.getPrice(), buy.getPrice()) && Objects.equals(sell.getQuantity(), buy.getQuantity())) {
				pushOrderConfirm(buy, sell);
				register(buy, sell);
			}
		}));

		log.info("OrderProcessorImpl.checkMatch - End");
	}


	private void pushOrderConfirm(OrderDTO buy, OrderDTO sell) {
		try {
			walletService.orderFinished(TypeOrder.BUY, buy.getPrice(), buy.getQuantity(), buy.getWalletID());
			orderService.finished(buy);

			walletService.orderFinished(TypeOrder.SELL, sell.getPrice(), sell.getQuantity(), sell.getWalletID());
			orderService.finished(sell);
		} catch (Exception e) {
			throw new BusinessException(ErrorCodes.BUSINESS_EXCEPTION.getMessage());
		}
	}

	private void register(OrderDTO buy, OrderDTO sell) {
//		registerService.registerOrder(buy);
//		registerService.registerOrder(sell);
	}

}
