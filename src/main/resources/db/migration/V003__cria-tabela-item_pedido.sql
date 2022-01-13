CREATE TABLE item_pedido (
	id UUID NOT NULL PRIMARY KEY,
	prodserv_id UUID NOT NULL,
	pedido_id UUID NOT NULL
);

ALTER TABLE item_pedido 
ADD CONSTRAINT fk_produto_servico foreign key (prodserv_id) references produto_servico (id);

ALTER TABLE item_pedido 
ADD CONSTRAINT fk_pedido foreign key (pedido_id) references pedido (id);