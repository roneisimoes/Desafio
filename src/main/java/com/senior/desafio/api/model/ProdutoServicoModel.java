package com.senior.desafio.api.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.senior.desafio.domain.model.Tipo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoServicoModel {

	private UUID id;
	
	@NotNull
	private Tipo tipo;
	
	@NotNull
	@Min(0)
	private BigDecimal valor;
	
	@NotNull
	private boolean ativo;
}
