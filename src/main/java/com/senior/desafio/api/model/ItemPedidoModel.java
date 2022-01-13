package com.senior.desafio.api.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoModel {

	private UUID id;
	
	@NotNull
	private ProdutoServicoModel produtoServico;
	
}
