package com.senior.desafio.domain.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.senior.desafio.domain.model.Pedido;
import com.senior.desafio.domain.model.Situacao;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

	@Query("FROM Pedido p WHERE (:situacao is null or p.situacao = :situacao)")
	Page<Pedido> search(@Param("situacao") Situacao situacao, Pageable pageable);
	
}
