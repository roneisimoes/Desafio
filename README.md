<h1 align="center">Desafio back-end</h1>

## **:rocket: OBJETIVO**

<p>O projeto tem como finalidade o desenvolvimento de um cadastro (Create/Read/Update/Delete/List com paginação)
para as seguintes entidades: produto/serviço, pedido e itens de pedido. Sendo possível aplicar filtros na listagem.
</p>
<p>
As entidades deverão utilizar Bean Validation e deverão ter um ID único do tipo UUID gerado automaticamente.
</p>
<p>
Deverá ser implementado um ControllerAdvice para customizar os HTTP Response das
requisições (mínimo BAD REQUEST).
</p>
<p>
No cadastro de produto/serviço deverá ter uma indicação para diferenciar um produto de
um serviço. Não deve ser possível excluir um produto/serviço se ele estiver associado a algum pedido
e adicionar um produto desativado em um pedido.
</p>
<p>
Deverá ser possível aplicar um percentual de desconto no pedido, porém apenas para os
itens que sejam produto (não serviço), o desconto será sobre o valor total dos produtos
e somente será possível aplicar desconto no pedido se ele estiver na situação Aberto
(Fechado bloqueia).
</p>

## **:computer: TECNOLOGIAS**

  - **Banco de dados PostgreSQL**
  - **Java 8+**
  - **Maven**
  - **Spring**
  - **JPA**
  - **Bean Validation**
  - **REST com JSON**
  
## **:pushpin: COMO UTILIZAR**
<p>
Para iniciar o projeto vai ser necessário configurar o Lombok na IDE, evitando que o projeto apresente erros de compilação em partes que utilizam algum método 
como getter/setter por exemplo. A configuração pode ser feita com base na documentação do projeto de acordo com a IDE que estiver utilizando no seguinte link
https://projectlombok.org.
</p>
<p>
Caso prefira seguir sem o Lombok pode estar comentando as anotações no projeto e gerando manualmente os métodos na ide de sua preferência.
</p>

<h2> Implementações API </h2>

## PRODUTO/SERVICO

### Listar Todos
[GET] ```localhost:8080/produtos_servicos```

Parametros -> $page(Nº pag), $quantity(Qtd de itens), tipo(PRODUTO/SERVICO), ativo(true,false)

### Listar Unico
[GET] ```localhost:8080/produtos_servicos/{produtoServicoId}```

### Adicionar
[POST] ```localhost:8080/produtos_servicos```

Body:
```JSON
{
	"tipo": "PRODUTO",
	"valor": 650,
	"ativo": true
}
```

### Atualizar
[PUT] ```localhost:8080/produtos_servicos/{produtoServicoId}```

Body:
```JSON
{
	"tipo": "PRODUTO",
	"valor": 700,
	"ativo": true
}
```
### Excluir
[DELETE] ```localhost:8080/produtos_servicos/{produtoServicoId}```

## PEDIDO

### Listar Todos
[GET] ```localhost:8080/pedidos```

Parametros -> $page(Nº pag), $quantity(Qtd de itens), situacao(ABERTO/FECHADO)

### Listar Unico
[GET] ```localhost:8080/pedidos/{pedidoId}```

### Adicionar
[POST] ```localhost:8080/pedidos```

Body:
```JSON
{
	"situacao": "FECHADO",
	"data": "2022-01-03T11:13:29.442608-03:00",
}
```

### Atualizar
[PUT] ```localhost:8080/pedidos/{pedidoId}```

Body:
```JSON
{
	"situacao": "FECHADO",
	"data": "2022-01-03T11:13:29.442608-03:00",
}
```
### Excluir
[DELETE] ```localhost:8080/pedidos/{pedidoId}```

### Adicionar item pedido
[PUT] ```localhost:8080/pedidos/{pedidoId}/adicionar-item```

Body:
```JSON
{
	"produtoServico": 
	{
		"id": "3921a15b-f0f6-407d-b101-16352a24857b"
	}
}
```

### Atualizar item pedido
[PUT] ```localhost:8080/pedidos/{pedidoId}/atualizar-item```

Body:
```JSON
{
	"produtoServico": 
	{
		"id": "3921a15b-f0f6-407d-b101-16352a24857b"
	}
}
```
### Excluir item do pedido
[PUT] ```localhost:8080/pedidos/{pedidoId}/excluir-item```

Body:
```JSON
{
	"id": "41201f2f-048a-4483-ad63-4b15b0eb78d0"
}
```

### Aplicar desconto ao pedido
[PUT] ```localhost:8080/pedidos/{pedidoId}/aplicar-desconto```

Body:
```JSON
{
	"desconto": 10
}
```

## Algumas das customizações HTTP Response das requisições:

Ao pesquisar por um id produto/servico inexistente:

![image](https://user-images.githubusercontent.com/55517128/149383092-bb62243a-906c-4916-9da6-16b93eb84853.png)

Ao cadastrar um produto/servico com valor vazio/nulo

![image](https://user-images.githubusercontent.com/55517128/149383545-edf2b003-6c6c-4337-830b-56400044277a.png)

Retorno busca/atualização de um produto/servico por id

![image](https://user-images.githubusercontent.com/55517128/149383904-dd04852a-5c41-4c35-bd7b-ea18d3a90a5d.png)

Retorno cadastro de um produto/servico

![image](https://user-images.githubusercontent.com/55517128/149384278-1c3c69db-cc1d-492e-835c-e8399367fec8.png)

<p>:exclamation: Não foram desenvolvidos os testes automatizados.</p>
<p>:question: Duvidas podem entrar em contato pelo email ronei.simoes19@gmail.com ou whats.</p>





