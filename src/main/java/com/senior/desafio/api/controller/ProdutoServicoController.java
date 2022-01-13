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

import com.senior.desafio.api.model.ProdutoServicoModel;
import com.senior.desafio.domain.model.Tipo;
import com.senior.desafio.domain.repository.ProdutoServicoRepository;
import com.senior.desafio.domain.service.ProdutoServicoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/produtos_servicos")
public class ProdutoServicoController {
	
	
	private ProdutoServicoService produtoServicoService;
	private ProdutoServicoRepository produtoServicoRepository;
	
	@GetMapping("/{produtoServicoId}")
	private ProdutoServicoModel buscarUm(@PathVariable UUID produtoServicoId) {
		return produtoServicoService.buscarUm(produtoServicoId);
	}
	
	@GetMapping
	private Page<ProdutoServicoModel> buscarTodos(@RequestParam(name = "$page") Integer pageNumber,
			@RequestParam(name = "$quantity") Integer quantity,
			@RequestParam(name = "tipo", required = false) Tipo tipo,
			@RequestParam(name = "ativo", required = false) boolean ativo) {
		PageRequest pageable = PageRequest.of(pageNumber, quantity);
		return produtoServicoService.buscarTodos(tipo, ativo, pageable);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoServicoModel adicionar(@Valid @RequestBody ProdutoServicoModel produtoServiModel) {
		return produtoServicoService.cadastrar(produtoServiModel);
	}
	
	@PutMapping("/{produtoServicoId}")
	public ResponseEntity<ProdutoServicoModel> atualizar(@Valid @PathVariable UUID produtoServicoId, 
				@RequestBody ProdutoServicoModel produtoServicoModel) {
		if (!produtoServicoRepository.existsById(produtoServicoId)) {
			return ResponseEntity.notFound().build();
		}
	
		produtoServicoModel = produtoServicoService.atualizar(produtoServicoId,produtoServicoModel);
		
		return ResponseEntity.ok(produtoServicoModel);
	}
	
	@DeleteMapping("/{produtoServicoId}")
	public ResponseEntity<Void> remover(@PathVariable UUID produtoServicoId) {
		if(!produtoServicoRepository.existsById(produtoServicoId)) {
			return ResponseEntity.notFound().build();
		}
		
		produtoServicoService.excluir(produtoServicoId);
		
		return ResponseEntity.noContent().build();
	}
	
	
}
