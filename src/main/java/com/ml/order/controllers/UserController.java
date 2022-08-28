package com.ml.order.controllers;

import com.ml.order.dtos.UserDTO;
import com.ml.order.services.UserService;
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

@Tag(name = "User Management", description = "Access to manipulaton User's")
@Slf4j
@RestController
@RequestMapping
public class UserController {

	@Autowired
	private UserService userService;

	@Operation(summary = "create")
	@PostMapping("/v1/users/create")
	public ResponseEntity<Void> create(@RequestBody @Valid UserDTO userDTO) {
		log.info("WalletController.create - Input - user.email:{} ", userDTO.getEmail());

		userService.create(userDTO);

		log.debug("WalletController.create - End - user: {}}", userDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Operation(summary = "update")
	@PutMapping("/v1/users/update")
	public ResponseEntity<Void> update(@RequestBody @Valid UserDTO userDTO) {
		log.info("WalletController.update - Input - user.email:{} ", userDTO.getEmail());

		userService.update(userDTO);

		log.debug("WalletController.update - End - user: {}}", userDTO);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@Operation(summary = "delete")
	@DeleteMapping("/v1/users/delete")
	public ResponseEntity<Void> delete(@RequestBody UserDTO userDTO) {
		log.info("WalletController.delete - Input - user.email:{} ", userDTO.getEmail());

		userService.delete(userDTO);

		log.debug("WalletController.delete - End - user: {}}", userDTO);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@Operation(summary = "list all")
	@GetMapping("/v1/users/list/")
	public ResponseEntity<Page<UserDTO>> listAll(Pageable pageable) {
		log.info("WalletController.delete - Input - Start");

		Page<UserDTO> users = userService.listAll(pageable);

		log.debug("WalletController.delete - End - size: {}}", users.getContent().size());
		return ResponseEntity.ok().body(users);
	}

}
