package com.ml.order.services;

import com.ml.order.constants.TypeOrder;
import com.ml.order.dtos.WalletDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletService {

	void create(WalletDTO walletDTO);

	void update(WalletDTO walletDTO);

	void delete(WalletDTO walletDTO);

	Page<WalletDTO> listAll(WalletDTO walletDTO, Pageable pageable);
	
	void orderFinished(TypeOrder type, Long walletId);
	
}
