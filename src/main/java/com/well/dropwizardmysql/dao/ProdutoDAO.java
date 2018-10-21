package com.well.dropwizardmysql.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.well.dropwizardmysql.domain.Produto;
import com.well.dropwizardmysql.mapper.ProdutoMapper;

@RegisterMapper(ProdutoMapper.class)
public interface ProdutoDAO {
	
	@SqlUpdate("CREATE DATABASE IF NOT EXISTS TESTEAPIDB;")
	void criarBanco();	

	@SqlUpdate("CREATE TABLE IF NOT EXISTS PRODUTO (ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,CODBARRAS VARCHAR(150), NOME VARCHAR(150), DESCRICAO VARCHAR(150), QUANTIDADE INT(20), CATEGORIA VARCHAR(150));")
	void criarTabela();

	@SqlQuery("select * from produto;")
	public List<Produto> getProdutos();
	
	@SqlQuery("select * from produto where id = :id")
	public Produto getProduto(@Bind("id") final int id);

	@SqlUpdate("insert into produto(codbarras, nome, descricao, quantidade, categoria) values(:codbarras, :nome, :descricao, :quantidade, :categoria)")
	void createProduto(@BindBean final Produto produto);

	@SqlUpdate("update produto set" + " codbarras = coalesce(:codbarras, codbarras), "
			+ " nome = coalesce(:nome, nome), " + " descricao = coalesce(:descricao, descricao), "
			+ " quantidade = coalesce(:quantidade, quantidade), " + " categoria = coalesce(:categoria, categoria) "
			+ " where id = :id")
	void editProduto(@BindBean final Produto produto);

	@SqlUpdate("delete from produto where id = :id")
	int deleteProduto(@Bind("id") final int id);

	@SqlQuery("select last_insert_id();")
	public int lastInsertId();

}
