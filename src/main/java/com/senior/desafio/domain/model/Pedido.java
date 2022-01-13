package com.senior.desafio.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
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
@Table(name = "pedido")
public class Pedido {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;
	
	@NotNull
	@Column(name = "data", nullable = false)
	private OffsetDateTime data;
	
	@NotNull
	@Min(0)
	@Column(name = "valor_total", nullable = false)
	private BigDecimal valorTotal;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "situacao", nullable = false)
	private Situacao situacao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pedido", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<ItemPedido> itens;
	
}
