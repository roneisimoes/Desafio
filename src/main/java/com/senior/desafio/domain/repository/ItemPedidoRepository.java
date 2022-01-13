package com.senior.desafio.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senior.desafio.domain.model.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {

}
