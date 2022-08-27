package com.ml.order.dtos;

import com.ml.order.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO implements Serializable {

	private static final long serialVersionUID = 7480632879288696331L;

	@NotNull(message = "Id is required")
	private Long id;

	@Builder.Default
	private BigDecimal balance = BigDecimal.ZERO;

	@Builder.Default
	private Long product = 0L;
	private User user;

}
