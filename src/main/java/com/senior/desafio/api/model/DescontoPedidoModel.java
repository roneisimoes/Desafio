package com.senior.desafio.api.model;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DescontoPedidoModel {

	@NotNull
	@Min(0)
	@Max(100)
	private BigDecimal desconto;
	
}
