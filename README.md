---
tags: [rest]
projects: [dropwizard-mysql]
---

== Descri��o

Criar controle de estoque para atender a necessidade de uma pequena papelaria. Esse sistema dever� ter as seguintes funcionalidades: cadastrar produto, remover produto, atualizar produto, listar produtos, exibir detalhes de um produto. Os atributos necess�rios para cadastrar um produto s�o: c�digo de barras, nome, descri��o, quantidade e categoria.

O objetivo � apresentar os conhecimentos na constru��o de um back end, com boas pr�ticas e teste focado ao banco de dados com as seguintes pilhas tecnol�gicas:

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
5 - Clique em Avan�ar "Next"
6 - Clique em Navegar "Browse" e selecione a pasta que � a raiz do projeto Maven (que cont�m o arquivo pom.xml)
7 - Clique em Avan�ar
8 - Clique em Finish
----

== Para Rodar o Aplicativo

----

1 - Voc� precisar� informar os dados de acesso ao seu banco de dados MySQL (src/main/resources/dropwizardmysql.yml)
2 - Na raiz da aplica��o execute o comando: mvn clean install
3 - Na raiz da aplica��o execute o comando: java -jar target\dropwizard-mysql-rest-1.0.0.jar src/main/resources/dropwizardmysql.yml

----

== Para Testar

----

Voc� precisar� informar os dados de acesso ao seu banco de dados MySQL (src/test/java/DbUnit.java)

----