package com.ml.order.services;

import com.ml.order.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

	void create(UserDTO userDTO);

	void update(UserDTO userDTO);

	void delete(UserDTO userDTO);

	Page<UserDTO> listAll(Pageable pageable);

}
