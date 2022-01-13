package com.senior.desafio.api.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.senior.desafio.api.model.DescontoPedidoModel;
import com.senior.desafio.api.model.ItemPedidoModel;
import com.senior.desafio.api.model.PedidoModel;
import com.senior.desafio.domain.model.Situacao;
import com.senior.desafio.domain.repository.PedidoRepository;
import com.senior.desafio.domain.service.PedidoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	private PedidoService pedidoService;
	private PedidoRepository pedidoRepository;
	
	@GetMapping("/{pedidoId}")
	private PedidoModel buscarUm(@PathVariable UUID pedidoId) {
		return pedidoService.buscarUm(pedidoId);
	}
	
	@GetMapping
	public Page<PedidoModel> buscarTodos(@RequestParam(name = "$page") Integer pageNumber,
			@RequestParam(name = "$quantity") Integer quantity,
			@RequestParam(name = "situacao", required = false) Situacao situacao){
		
		PageRequest pageable = PageRequest.of(pageNumber, quantity);
		return pedidoService.buscarTodos(pageable,situacao);
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoModel pedidoModel) {
		return pedidoService.salvar(pedidoModel);
	}

	@PutMapping("/{pedidoId}")
	public ResponseEntity<PedidoModel> atualizar(@PathVariable UUID pedidoId,@Valid @RequestBody PedidoModel pedidoModel) {
		if(!pedidoRepository.existsById(pedidoId)) {
			return ResponseEntity.notFound().build();
		}
		
		pedidoModel = pedidoService.atualizar(pedidoId,pedidoModel);
		
		return ResponseEntity.ok(pedidoModel);
	}
	
	@DeleteMapping("/{pedidoId}")
	public ResponseEntity<Void> remover(@PathVariable UUID pedidoId) {
		if(!pedidoRepository.existsById(pedidoId)) {
			return ResponseEntity.notFound().build();
		}
		
		pedidoService.excluir(pedidoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{pedidoId}/aplicar-desconto")
	public PedidoModel aplicarDesconto(@PathVariable UUID pedidoId, @RequestBody DescontoPedidoModel desconto) {
		return pedidoService.aplicarDesconto(pedidoId, desconto);
	}
	
	@PutMapping("/{pedidoId}/adicionar-item")
	public PedidoModel adicionarItem(@PathVariable UUID pedidoId, @Valid @RequestBody ItemPedidoModel item) {
		return pedidoService.adicionarItem(pedidoId, item);
	};
	
	@PutMapping("/{pedidoId}/atualizar-item")
	public PedidoModel atualizarItem(@PathVariable UUID pedidoId, @Valid @RequestBody ItemPedidoModel item) {
		return pedidoService.atualizarItem(pedidoId, item);
	};
	
	@PutMapping("/{pedidoId}/excluir-item")
	public PedidoModel excluirItem(@PathVariable UUID pedidoId, @RequestBody ItemPedidoModel item) {
		return pedidoService.excluirItem(pedidoId, item);
	};
	
}
