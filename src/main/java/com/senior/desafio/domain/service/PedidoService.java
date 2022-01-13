package com.senior.desafio.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senior.desafio.api.model.DescontoPedidoModel;
import com.senior.desafio.api.model.ItemPedidoModel;
import com.senior.desafio.api.model.PedidoModel;
import com.senior.desafio.domain.exception.EntidadeNaoEncontradaException;
import com.senior.desafio.domain.exception.NegocioException;
import com.senior.desafio.domain.model.ItemPedido;
import com.senior.desafio.domain.model.Pedido;
import com.senior.desafio.domain.model.Situacao;
import com.senior.desafio.domain.model.Tipo;
import com.senior.desafio.domain.repository.PedidoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PedidoService {

	private PedidoRepository pedidoRepository;
	private ItemPedidoService itemPedidoService;
	private ProdutoServicoService produtoServicoService;

	protected PedidoModel entityToDTO(Pedido pedido) {
		PedidoModel pedidoModel = new PedidoModel();
		pedidoModel.setId(pedido.getId());
		pedidoModel.setSituacao(pedido.getSituacao());
		pedidoModel.setValorTotal(pedido.getValorTotal());
		pedidoModel.setData(pedido.getData());
		List<ItemPedidoModel> itens = pedido.getItens().stream().map(i -> itemPedidoService.entityToDTO(i)).collect(Collectors.toList());
		pedidoModel.setItens(itens);
		return pedidoModel;
	}

	protected Pedido dtoToEntity(PedidoModel pedidoModel) {
		Pedido pedido = new Pedido();
		pedido.setId(pedidoModel.getId());
		pedido.setSituacao(pedidoModel.getSituacao());
		pedido.setValorTotal(pedidoModel.getValorTotal());
		pedido.setData(pedidoModel.getData());
		List<ItemPedido> itens = pedidoModel.getItens().stream().map(i -> {
			ItemPedido itemPedido = itemPedidoService.dtoToEntity(i);
			itemPedido.setPedido(pedido);
			return itemPedido;
		}).collect(Collectors.toList());
		pedido.setItens(itens);
		return pedido;
	}
	
	public PedidoModel buscarUm(UUID id) {
		return buscarUmPorId(id);
	}
	
	private PedidoModel buscarUmPorId(UUID id) {
		Optional<Pedido> optional = pedidoRepository.findById(id);
		if (!optional.isPresent()) {
			throw new EntidadeNaoEncontradaException("Pedido não encontrado");
		}
		Pedido pedido = optional.get();
		return entityToDTO(pedido);
	}
	
	public Page<PedidoModel> buscarTodos(Pageable pageable, Situacao situacao) {
		Page<Pedido> buscarTodos = pedidoRepository.search(situacao, pageable);
		List<PedidoModel> listDtos = buscarTodos.getContent().stream().map(p -> entityToDTO(p)).collect(Collectors.toList());
		return new PageImpl<>(listDtos, pageable, buscarTodos.getTotalElements());
	}
	
	@Transactional
	public PedidoModel salvar(PedidoModel pedidoModel) {
		Pedido pedido = pedidoRepository.save(dtoToEntity(pedidoModel));
		atualizarValorTotal(pedido);
		
		return entityToDTO(pedido); 
	}
	
	@Transactional
	public PedidoModel atualizar(UUID id, PedidoModel pedidoModel) {
		pedidoModel.setId(id);
		PedidoModel pedido = buscarUmPorId(id);
		
		pedidoModel.setItens(pedido.getItens());
		Pedido pedidoSave = pedidoRepository.save(dtoToEntity(pedidoModel));
		atualizarValorTotal(pedidoSave);
		return entityToDTO(pedidoSave);
	}
	
	@Transactional
	public void excluir(UUID pedidoId) {
		Pedido pedido = dtoToEntity(buscarUmPorId(pedidoId));
		pedidoRepository.delete(pedido);
	}
	
	
	public void atualizarValorTotal(Pedido pedido) {
		atualizarValorTotal(pedido, BigDecimal.ZERO);
	}
	
	public void atualizarValorTotal(Pedido pedido, BigDecimal desconto) {
		BigDecimal valorTotalProduto = pedido.getItens().stream()
				.filter(i -> i.getProdutoServico().ehUmProduto())
				.map(i -> i.getProdutoServico().getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);	
		BigDecimal valorTotalServico = pedido.getItens().stream()
				.filter(i -> i.getProdutoServico().ehUmServico())
				.map(i -> i.getProdutoServico().getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal valorDesconto = desconto.multiply(valorTotalProduto).multiply(new BigDecimal("0.01"));
		
		pedido.setValorTotal(valorTotalProduto.add(valorTotalServico).subtract(valorDesconto));
		
	}
	
	@Transactional
	public PedidoModel aplicarDesconto(UUID id, DescontoPedidoModel desconto) {

		PedidoModel pedidoModel = buscarUmPorId(id);

		if (pedidoModel.pedidoFechado()) {
			throw new NegocioException("Não é possivel aplicar desconto em um pedido fechado!");
		}

		Pedido pedido = dtoToEntity(pedidoModel);
		atualizarValorTotal(pedido,desconto.getDesconto());
		pedido = pedidoRepository.save(pedido);

		return entityToDTO(pedido);
	}
	
	@Transactional
	public PedidoModel adicionarItem(UUID id, ItemPedidoModel item) {

		if (!produtoServicoService.buscarUm(item.getProdutoServico().getId()).isAtivo() &&
				produtoServicoService.buscarUm(item.getProdutoServico().getId()).getTipo() == Tipo.PRODUTO) {
			throw new NegocioException("Não é possivel adicionar um produto desativado a um pedido!");
		}

		PedidoModel pedidoModel = buscarUm(id);
		pedidoModel.getItens().add(item);

		Pedido pedido = dtoToEntity(pedidoModel);
		atualizarValorTotal(pedido);
		pedido = pedidoRepository.save(pedido);
		return entityToDTO(pedido);
	}
	
	@Transactional
	public PedidoModel atualizarItem(UUID id, ItemPedidoModel item) {

		PedidoModel pedidoModel = buscarUm(id);
		for (ItemPedidoModel i : pedidoModel.getItens()) {
			if (i.getId().equals(id)) {
				i = item;
				break;
			}
		}

		Pedido pedido = dtoToEntity(pedidoModel);
		pedido = pedidoRepository.save(pedido);
		return entityToDTO(pedido);
	}
	
	@Transactional
	public PedidoModel excluirItem(UUID id, ItemPedidoModel item) {
		PedidoModel pedidoModel = buscarUm(id);

		List<ItemPedidoModel> itens = pedidoModel.getItens().stream().filter(i -> !i.getId().equals(item.getId())).collect(Collectors.toList());
		pedidoModel.getItens().clear();
		pedidoModel.setItens(itens);

		Pedido pedido = dtoToEntity(pedidoModel);
		atualizarValorTotal(pedido);
		pedido = pedidoRepository.save(pedido);
		return entityToDTO(pedido);
	}
}
