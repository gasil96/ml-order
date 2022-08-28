package com.ml.order.dtos;

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

	private Long id;
	private String name;

	@NotNull(message = "Email is required")
	@Email(message = "Insert valid email")
	private String email;
	private WalletDTO wallet;

}
