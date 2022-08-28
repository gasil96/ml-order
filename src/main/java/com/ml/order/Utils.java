package com.ml.order;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class Utils {

	public static <D, T> Page<D> mapEntityPageIntoDtoPage(Page<T> entities, Class<D> dtoClass) {
		ModelMapper mapper = new ModelMapper();
		return entities.map(objectEntity -> mapper.map(objectEntity, dtoClass));
	}

}
