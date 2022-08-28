package com.ml.order.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO implements Serializable {

	private static final long serialVersionUID = 7480632879288696331L;

	private Long id;

	@Builder.Default
	private BigDecimal balance = BigDecimal.ZERO;

	@Builder.Default
	private Long product = 0L;
	@JsonIgnore
	private UserDTO user;

}
