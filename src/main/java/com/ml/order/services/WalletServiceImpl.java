package com.ml.order.services;

import com.ml.order.Utils;
import com.ml.order.constants.ErrorCodes;
import com.ml.order.constants.TypeOrder;
import com.ml.order.dtos.WalletDTO;
import com.ml.order.entities.Wallet;
import com.ml.order.exceptions.BusinessException;
import com.ml.order.repositories.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private WalletRepository walletRepository;

	@Override
	public void create(WalletDTO walletDTO) {
		log.info("WalletServiceImpl.create - Input - wallet.user.email: {} ", walletDTO.getUser().getEmail());
		Wallet wallet = mapper.map(walletDTO, Wallet.class);

		walletRepository.save(wallet);
		log.debug("WalletServiceImpl.create - End - wallet: {}", walletDTO);
	}

	@Override
	public Page<WalletDTO> listAll(Pageable pageable) {
		log.info("WalletServiceImpl.listAll - Input - pageSize: {} ", pageable.getPageSize());

		Page<Wallet> entities = walletRepository.findAll(pageable);
		Page<WalletDTO> walletsDTO = Utils.mapEntityPageIntoDtoPage(entities, WalletDTO.class);

		log.debug("WalletServiceImpl.listAll - End - listSize: {} ", walletsDTO.getContent().size());
		return walletsDTO;
	}

	@Override
	public void orderFinished(TypeOrder type, BigDecimal orderPrice, Long orderQuantity, Long walletId) {
		log.info("WalletServiceImpl.orderFinished - Input - type: {}, walletId: {} ", type, walletId);

		Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> {
			log.error("WalletServiceImpl.orderFinished - Error - Message: Not found wallet");
			throw new BusinessException(ErrorCodes.WALLET_NOT_FOUND_EXCEPTION.getMessage());
		});

		calculateOrderFinished(type, orderPrice, orderQuantity, wallet);
		log.info("WalletServiceImpl.orderFinished - Input - type: {}, walletId: {} ", type, walletId);
	}

	private void calculateOrderFinished(TypeOrder type, BigDecimal orderPrice, Long orderQuantity, Wallet wallet) {
		if (TypeOrder.BUY.equals(type)) {
			wallet.setBalance(wallet.getBalance().subtract(orderPrice));
			wallet.setProduct(wallet.getProduct() + orderQuantity);
		}

		if (TypeOrder.SELL.equals(type)) {
			wallet.setBalance(wallet.getBalance().add(orderPrice));
			wallet.setProduct(wallet.getProduct() - orderQuantity);
		}

		walletRepository.save(wallet);
	}

}
