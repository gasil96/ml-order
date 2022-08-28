package com.ml.order.services;

import com.ml.order.constants.TypeOrder;
import com.ml.order.dtos.WalletDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface WalletService {

	void create(WalletDTO walletDTO);

	Page<WalletDTO> listAll(Pageable pageable);
	
	void orderFinished(TypeOrder type, BigDecimal orderPrice, Long orderQuantity, Long walletId);
	
}
