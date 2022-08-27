package com.ml.order.entities;


import com.ml.order.constants.StatusOrder;
import com.ml.order.constants.TypeOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order")
public class Order implements Serializable {

	private static final long serialVersionUID = -6072150589970547074L;

	@Id
	@NotNull
	private String id;

	@NotNull
	@Field("type")
	private TypeOrder typeOrder;

	@NotNull
	@Field("status")
	private StatusOrder statusOrder;

	@NotNull
	private BigDecimal price;

	@NotNull
	private Long walletBuyId;

	@NotNull
	private Long walletSellId;

	@Field("Date of operation")
	private LocalDateTime dateOp;

}
