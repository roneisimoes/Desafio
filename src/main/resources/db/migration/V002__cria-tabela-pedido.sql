CREATE TABLE pedido (
	id UUID NOT NULL PRIMARY KEY,
	data TIMESTAMP NOT NULL,
	valor_total DECIMAL(10,2) NOT NULL,
	situacao VARCHAR(20) NOT NULL
);