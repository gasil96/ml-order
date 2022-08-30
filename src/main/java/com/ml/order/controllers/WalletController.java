package com.ml.order.controllers;

import com.ml.order.dtos.WalletDTO;
import com.ml.order.services.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@Tag(name = "Wallet Management", description = "Access to manipulaton Wallet's")
@Slf4j
@RestController
@RequestMapping
public class WalletController {

	@Autowired
	private WalletService walletService;

	@Operation(summary = "add wallet in user")
	@Deprecated
	@PostMapping("/v1/wallets/add")
	public ResponseEntity<Void> addInUser(@Valid @RequestBody WalletDTO walletDTO) {
		log.info("WalletController.add - Input - wallet.user.email:{} ", walletDTO.getUser().getEmail());

		walletService.create(walletDTO);

		log.debug("WalletController.add - End - wallet: {}}", walletDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "list all")
	@GetMapping("/v1/wallets/list")
	public ResponseEntity<Page<WalletDTO>> listAll(Pageable pageable) {
		log.info("WalletController.listAll - Input");

		Page<WalletDTO> wallets = walletService.listAll(pageable);

		log.debug("WalletController.listAll - End - size: {}}", wallets.getContent().size());
		return ResponseEntity.ok().body(wallets);
	}

}
