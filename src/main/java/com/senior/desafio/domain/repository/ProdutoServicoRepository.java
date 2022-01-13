package com.senior.desafio.domain.repository;


import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.senior.desafio.domain.model.ProdutoServico;
import com.senior.desafio.domain.model.Tipo;

@Repository
public interface ProdutoServicoRepository extends JpaRepository<ProdutoServico, UUID> {
	
	@Query("FROM ProdutoServico p WHERE (:tipo is null or p.tipo = :tipo) and (:ativo is null or p.ativo = :ativo)")
	Page<ProdutoServico> search(@Param("tipo") Tipo tipo, @Param("ativo") boolean ativo, Pageable pageable);
	
}
