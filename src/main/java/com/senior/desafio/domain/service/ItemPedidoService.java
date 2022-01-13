package com.senior.desafio.domain.service;

import org.springframework.stereotype.Service;

import com.senior.desafio.api.model.ItemPedidoModel;
import com.senior.desafio.domain.model.ItemPedido;
import com.senior.desafio.domain.model.ProdutoServico;
import com.senior.desafio.domain.repository.ProdutoServicoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ItemPedidoService {

	private ProdutoServicoService produtoServicoService;
	private ProdutoServicoRepository produtoServicoRepository;
	
	protected ItemPedidoModel entityToDTO(ItemPedido itemPedido) {
		ItemPedidoModel item = new ItemPedidoModel();
		item.setId(itemPedido.getId());
		item.setProdutoServico(produtoServicoService.buscarUm(itemPedido.getProdutoServico().getId()));
		
		return item;
	}
	
	protected ItemPedido dtoToEntity(ItemPedidoModel itemPedidoModel) {
		ItemPedido item = new ItemPedido();
		item.setId(itemPedidoModel.getId());
		ProdutoServico produtoServico = produtoServicoRepository.findById(itemPedidoModel.getProdutoServico().getId()).get();
		item.setProdutoServico(produtoServico);
		
		return item;
	}
	
}
