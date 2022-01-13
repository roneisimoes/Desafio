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

