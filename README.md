---
tags: [rest]
projects: [dropwizard-mysql]
---

== Descrição

Criar controle de estoque para atender a necessidade de uma pequena papelaria. Esse sistema deverá ter as seguintes funcionalidades: cadastrar produto, remover produto, atualizar produto, listar produtos, exibir detalhes de um produto. Os atributos necessários para cadastrar um produto são: código de barras, nome, descrição, quantidade e categoria.

O objetivo é apresentar os conhecimentos na construção de um back end, com boas práticas e teste focado ao banco de dados com as seguintes pilhas tecnológicas:

* Dropwizard
* MySql
* JUnit
* DbUnit

== Recursos

HTTP GET:

----
1. http://localhost:4000/produto
2. http://localhost:4000/produto/id
3. http://localhost:4000/admin
4. http://localhost:4000/ping
----

HTTP DELETE:

----
1. http://localhost:4000/produto/id
----

HTTP PUT:

----
1. http://localhost:4000/produto/id
----

1 - Abra o Eclipse
2 - Clique em File > Import
3 - Digite Maven na caixa de pesquisa em Selecione uma escrita, "Select an import source:"
4 - Selecionar projetos Maven existentes "Existing Maven Projects"
5 - Clique em Avançar "Next"
6 - Clique em Navegar "Browse" e selecione a pasta que é a raiz do projeto Maven (que contém o arquivo pom.xml)
7 - Clique em Avançar
8 - Clique em Finish
----

== Para Rodar o Aplicativo

----

1 - Você precisará informar os dados de acesso ao seu banco de dados MySQL (src/main/resources/dropwizardmysql.yml)
2 - Na raiz da aplicação execute o comando: mvn clean install
3 - Na raiz da aplicação execute o comando: java -jar target\dropwizard-mysql-rest-1.0.0.jar src/main/resources/dropwizardmysql.yml

----

== Para Testar

----

Você precisará informar os dados de acesso ao seu banco de dados MySQL (src/test/java/DbUnit.java)

----