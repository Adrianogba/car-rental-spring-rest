# Desafio Spring - Reserva de Veículos
<img src="https://spring.io/img/spring-by-pivotal.png" width="55%">

Neste código foi feito um webservice REST com operações para o registro de Veículos, Clientes, o Aluguél de Veículos e Reserva de Veículos.

### Especificações principais

* [Spring](https://spring.io/) - v5.1.2.RELEASE - Framework Web Java/Kotlin MVW
* [Spring Boot](https://spring.io/projects/spring-boot) - v2.1.0.RELEASE - Framework Initializer
* [Java](https://www.java.com/) - v1.8.191-b12 - Linguagem
* [Hibernate](http://hibernate.org/orm/) - v5.3.7.Final - ORM
* [Tomcat](http://tomcat.apache.org/) - v9.0.12 - Servidor
* [Swagger](https://swagger.io/) - v2.7.0 - Gerenciador de Documentação e Testes Funcionais
* [MySQL](https://www.mysql.com/) - v8.0.12 - SGBD
* [Intellij IDEA](https://www.jetbrains.com/idea/) - v2018.2.5 - IDE

### Instalação

Clone o projeto e importe com a IDE suportada que lhe convir como um projeto Maven.

Caso venha a usar o MySQL como persistencia o projeto já está com o driver do mesmo instalado, então vá para:
```
src > main > resources > application.properties
```
E ajuste os dados de Endereço, Timezone, Usuário e Senha de acordo com as configurações do seu banco.

Caso venha a utilizar outro banco será necessário adicionar o Driver do mesmo no pom.xml. Após isso, vá ao application.properties e ajuste os dados de acordo com o seu SGBD.


Agora aguarde sua IDE baixar as dependencias ou execute o mvn spring-boot:run caso tenha configurado o Maven separadamente.


### Rotas
Estas são as rodas para uso deste webservice:

#### Clientes:
| Função | Rota | Parametro | Tipo |
| ------ | ------ | ------ | ------ |
| Listar Todos | /clientes | Nenhum | GET
| Exibir | /cliente/id | ID do Cliente | GET
| Cadastrar | /cliente | JSON do Cliente + Confirmação de senha | POST
| Editar | /cliente/id | ID do Cliente | PUT
| Remover | /cliente/id | ID do Cliente | DELETE


#### Veículos:
| Função | Rota | Parametro | Tipo |
| ------ | ------ | ------ | ------ |
| Listar Todos | /veiculos | Nenhum | GET
| Exibir | /veiculo/id | ID do Veículo | GET
| Cadastrar | /veiculo | JSON do Veículo | POST
| Editar | /veiculo/id | ID do Veículo | PUT
| Remover | /veiculo/id | ID do Veículo | DELETE

#### Efetuar Reserva de Veículo:
| Função | Rota | Parametro | Tipo |
| ------ | ------ | ------ | ------ |
| Listar Todas | /reservas | Nenhum | GET
| Exibir | /reservas/id | ID da Reserva | GET
| Cadastrar | /reservas | JSON do Reserva | POST
| Editar | /reservas/id | ID do Reserva | PUT
| Remover | /reservas/id | ID do Reserva | DELETE
| Listar Todas Referentes a um Cliente em Específico | /reservas/id_cliente | Nenhum | GET
| Listar Todas Referentes a um Veículo em Específico | /reservas/id_veiculo | Nenhum | GET

#### Aluguel de Veículo:
| Função | Rota | Parametro | Tipo |
| ------ | ------ | ------ | ------ |
| Listar Todas | /alugueis | Nenhum | GET
| Exibir | /aluguel/id | ID da Aluguel | GET
| Cadastrar | /aluguel | JSON do Aluguel | POST
| Editar | /aluguel/id | ID do Aluguel | PUT
| Remover | /aluguel/id | ID do Aluguel | DELETE
| Listar Todas Referentes a um Cliente em Específico | /aluguel/id_cliente | Nenhum | GET
| Listar Todas Referentes a um Veículo em Específico | /aluguel/id_veiculo | Nenhum | GET


## Para mais detalhes e efetuar os testes, basta executar o Swagger do projeto pelo endereço:
```
http://localhost:8080/swagger-ui.html
```
