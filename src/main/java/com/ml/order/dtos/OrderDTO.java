package com.ml.order.dtos;

import com.ml.order.constants.StatusOrder;
import com.ml.order.constants.TypeOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 186572189214748082L;

	@Id
	@NotNull(message = "Id is required")
	private String id;

	@NotNull(message = "Type operation is required")
	private TypeOrder typeOrder;

	@NotNull(message = "Status operation is required")
	private StatusOrder statusOrder;

	@NotNull(message = "Price is required")
	private BigDecimal price;

	@NotNull(message = "Wallet of Buy ID is required")
	private Long walletBuyId;

	@NotNull(message = "Wallet of Sell ID is required")
	private Long walletSellId;
	private LocalDateTime dateOp;

}
