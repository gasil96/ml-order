package com.ml.order.services;

import com.ml.order.constants.TypeOrder;
import com.ml.order.dtos.WalletDTO;
import com.ml.order.entities.Wallet;
import com.ml.order.repositories.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
	public void update(WalletDTO walletDTO) {
		log.info("WalletServiceImpl.update - Input - wallet.user.email: {} ", walletDTO.getUser().getEmail());
		Wallet wallet = mapper.map(walletDTO, Wallet.class);

		walletRepository.save(wallet);
		log.debug("WalletServiceImpl.update - End - wallet: {}", walletDTO);
	}

	@Override
	public void delete(WalletDTO walletDTO) {
		log.info("WalletServiceImpl.delete - Input - wallet.user.email: {} ", walletDTO.getUser().getEmail());
		Wallet wallet = mapper.map(walletDTO, Wallet.class);

		walletRepository.delete(wallet);
		log.debug("WalletServiceImpl.delete - End - wallet: {}", walletDTO);
	}

	@Override
	public Page<WalletDTO> listAll(WalletDTO walletDTO, Pageable pageable) {
		log.info("WalletServiceImpl.listAll - Input - pageSize: {} ", pageable.getPageSize());

		Page<Wallet> entities = walletRepository.findAll(pageable);
		Page<WalletDTO> walletsDTO = mapEntityPageIntoDtoPage(entities, WalletDTO.class);

		log.debug("WalletServiceImpl.listAll - End - listSize: {} ", walletsDTO.getContent().size());
		return walletsDTO;
	}

	@Override
	public void orderFinished(TypeOrder type, Long walletId) {
		log.info("WalletServiceImpl.orderFinished - Input - type: {}, walletId: {} ", type, walletId);

		//TODO: Pedding implementation

		log.info("WalletServiceImpl.orderFinished - Input - type: {}, walletId: {} ", type, walletId);
	}

	private <D, T> Page<D> mapEntityPageIntoDtoPage(Page<T> entities, Class<D> dtoClass) {
		return entities.map(objectEntity -> mapper.map(objectEntity, dtoClass));
	}

}
