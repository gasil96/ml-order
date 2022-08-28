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
	private String id;

	@NotNull(message = "Type operation is required")
	private TypeOrder typeOrder;

	private StatusOrder statusOrder;

	@NotNull(message = "Price is required")
	private BigDecimal price;

	@NotNull(message = "Quantity is required")
	private Long quantity;

	@NotNull(message = "Wallet ID is required")
	private Long walletID;
	private LocalDateTime dateOp;

}
