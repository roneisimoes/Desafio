package com.senior.desafio.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.senior.desafio.domain.model.Situacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoModel {

	private UUID id;
	
	@NotNull
	private OffsetDateTime data;
	
	@NotNull
	@Min(0)
	private BigDecimal valorTotal = BigDecimal.ZERO;
	
	@NotNull
	private Situacao situacao;
	
	private List<ItemPedidoModel> itens = new ArrayList<>(0);
	
	public boolean pedidoAberto() {
		return Situacao.ABERTO.equals(getSituacao());
	}
	
	public boolean pedidoFechado() {
		return !pedidoAberto();
	}
}
