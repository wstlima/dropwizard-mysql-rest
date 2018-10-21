package com.well.dropwizardmysql.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.well.dropwizardmysql.domain.Produto;

public class ProdutoMapper implements ResultSetMapper<Produto> {

	private static final String ID = "id";
	private static final String CODBARRAS = "codBarras";
	private static final String NOME = "nome";
	private static final String DESCRICAO = "descricao";
	private static final String QUANTIDADE = "quantidade";
	private static final String CATEGORIA = "categoria";	
    
	public Produto map(int i, ResultSet resultSet, StatementContext statementContext) 
			throws SQLException {
				Produto produto = new Produto(
		    		  						resultSet.getString(CODBARRAS), 
		    		  						resultSet.getString(NOME),
		    		  						resultSet.getString(DESCRICAO),
		    		  						resultSet.getInt(QUANTIDADE),
		    		  						resultSet.getString(CATEGORIA)
	    		  							);
	       produto.setId(resultSet.getInt(ID));
	       return produto;
	}
}
