package com.ml.order.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusOrder {

	ERRO("Error"), IN_TRANSACTION("In transaction"), FINISHED("Finished");

	private String description;

}
