package com.senior.desafio.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "produto_servico")
public class ProdutoServico {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false)
	private Tipo tipo;
	
	@NotNull
	@Min(0)
	@Column(name = "valor", nullable = false)
	private BigDecimal valor;
	
	@NotNull
	@Column(name = "ativo")
	private boolean ativo;
	
	@OneToMany(mappedBy = "produtoServico", fetch = FetchType.LAZY)
	private List<ItemPedido> itensPedido;
	
	public boolean ehUmProduto() {
		return Tipo.PRODUTO.equals(getTipo());
	}
	
	public boolean ehUmServico() {
		return Tipo.SERVICO.equals(getTipo());
	}

}
