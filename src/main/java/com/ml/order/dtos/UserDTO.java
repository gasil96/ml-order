package com.ml.order.dtos;

import com.ml.order.entities.Wallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 4816714631153917072L;

	@NotNull(message = "Id is required")
	private Long id;
	private String name;

	@NotNull(message = "Email is required")
	@Email(message = "Insert valid email")
	private String email;
	private Wallet wallet;

}