package com.senior.desafio.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senior.desafio.api.model.ProdutoServicoModel;
import com.senior.desafio.domain.exception.EntidadeNaoEncontradaException;
import com.senior.desafio.domain.model.ProdutoServico;
import com.senior.desafio.domain.model.Tipo;
import com.senior.desafio.domain.repository.ProdutoServicoRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProdutoServicoService {

	private ProdutoServicoRepository produtoServicoRepository;
	
	protected ProdutoServicoModel entityToDTO(ProdutoServico produtoServico) {
		ProdutoServicoModel produtoServicoModel = new ProdutoServicoModel();
		produtoServicoModel.setId(produtoServico.getId());
		produtoServicoModel.setTipo(produtoServico.getTipo());
		produtoServicoModel.setValor(produtoServico.getValor());
		produtoServicoModel.setAtivo(produtoServico.isAtivo());
		return produtoServicoModel;
	}

	protected ProdutoServico dtoToEntity(ProdutoServicoModel produtoServicoModel) {
		ProdutoServico produtoServico = new ProdutoServico();
		produtoServico.setId(produtoServicoModel.getId());
		produtoServico.setTipo(produtoServicoModel.getTipo());
		produtoServico.setValor(produtoServicoModel.getValor());
		produtoServico.setAtivo(produtoServicoModel.isAtivo());
		return produtoServico;
	}
	
	
	public ProdutoServicoModel buscarUmPorId(UUID id) {
		Optional<ProdutoServico> optional = produtoServicoRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new EntidadeNaoEncontradaException("Produto/Serviço não encontrado!"); 
		}
		ProdutoServico produtoServico = optional.get();
		return entityToDTO(produtoServico);
	}
	
	public ProdutoServicoModel buscarUm(UUID id) {
		return buscarUmPorId(id);
	}
	
	public Page<ProdutoServicoModel> buscarTodos(Tipo tipo, boolean ativo, Pageable pageable) {
		Page<ProdutoServico> buscarTodos = produtoServicoRepository.search(tipo, ativo, pageable);
		List<ProdutoServicoModel> listDtos = buscarTodos.getContent().stream().map(p -> entityToDTO(p)).collect(Collectors.toList());
		return new PageImpl<>(listDtos, pageable, buscarTodos.getTotalElements());
	}
	
	@Transactional
	public ProdutoServicoModel cadastrar(ProdutoServicoModel produtoServicoModel) {
		ProdutoServico produtoServico = produtoServicoRepository.save(dtoToEntity(produtoServicoModel));
		return entityToDTO(produtoServico);
	}

	@Transactional
	public ProdutoServicoModel atualizar(UUID id, ProdutoServicoModel produtoServicoModel) {
		produtoServicoModel.setId(id);
		ProdutoServico produtoServico = produtoServicoRepository.save(dtoToEntity(produtoServicoModel));
		return entityToDTO(produtoServico);
	}

	@Transactional
	public void excluir(UUID id) {
		ProdutoServico produtoServico = dtoToEntity(buscarUmPorId(id));
		
		produtoServicoRepository.delete(produtoServico);
	};
	
}
