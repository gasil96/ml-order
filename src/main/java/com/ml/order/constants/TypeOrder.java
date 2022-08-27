package com.ml.order.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeOrder {

	BUY("Buy"), SELL("Sell");

	private String description;

}
