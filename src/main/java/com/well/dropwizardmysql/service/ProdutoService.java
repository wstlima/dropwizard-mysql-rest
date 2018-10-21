package com.well.dropwizardmysql.service;

import java.util.List;
import java.util.Objects;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.skife.jdbi.v2.exceptions.UnableToObtainConnectionException;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.well.dropwizardmysql.dao.ProdutoDAO;
import com.well.dropwizardmysql.domain.Produto;

public abstract class ProdutoService {

	private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

	private static final String DATABASE_ACCESS_ERROR = "Não foi possível acessar o banco de dados MySQL. O banco de dados pode estar inoperante ou pode haver problemas de conectividade de rede. Detalhes: ";
	private static final String DATABASE_CONNECTION_ERROR = "Não foi possível criar uma conexão com o banco de dados MySQL. As configurações do banco de dados estão provavelmente incorretas. Detalhes: ";
	private static final String UNEXPECTED_DATABASE_ERROR = "Ocorreu um erro inesperado ao tentar acessar o banco de dados. Detalhes: ";
	private static final String SUCCESS = "Sucesso";
	private static final String UNEXPECTED_DELETE_ERROR = "Ocorreu um erro inesperado ao excluir o produto.";
	private static final String PRODUTO_NOT_FOUND = "O Produto com o ID %s não foi encontrado.";

	@CreateSqlObject
	abstract ProdutoDAO produtoDAO();

	public List<Produto> getProdutos() {
		return produtoDAO().getProdutos();
	}

	public Produto getProduto(int id) {
		Produto produto = produtoDAO().getProduto(id);
		if (Objects.isNull(produto)) {
			throw new WebApplicationException(String.format(PRODUTO_NOT_FOUND, id), Status.NOT_FOUND);
		}
		return produto;
	}

	public Produto createProduto(Produto produto) throws Exception {
		produtoDAO().createProduto(produto);
		return produtoDAO().getProduto(produtoDAO().lastInsertId());
	}

	public Produto editProduto(Produto produto) {
		if (Objects.isNull(produtoDAO().getProduto(produto.getId()))) {
			throw new WebApplicationException(String.format(PRODUTO_NOT_FOUND, produto.getId()), Status.NOT_FOUND);
		}
		produtoDAO().editProduto(produto);
		return produtoDAO().getProduto(produto.getId());
	}

	public String deleteProduto(final int id) {
		int result = produtoDAO().deleteProduto(id);
		logger.info("Resultado em ProdutoService.deleteProduto é: {}", result);
		switch (result) {
		case 1:
			return SUCCESS;
		case 0:
			throw new WebApplicationException(String.format(PRODUTO_NOT_FOUND, id), Status.NOT_FOUND);
		default:
			throw new WebApplicationException(UNEXPECTED_DELETE_ERROR, Status.INTERNAL_SERVER_ERROR);
		}
	}

	public String performHealthCheck() {
		try {
			produtoDAO().criarBanco();
			produtoDAO().criarTabela();
			produtoDAO().getProdutos();
		} catch (UnableToObtainConnectionException ex) {
			return checkUnableToObtainConnectionException(ex);
		} catch (UnableToExecuteStatementException ex) {
			return checkUnableToExecuteStatementException(ex);
		} catch (Exception ex) {
			return UNEXPECTED_DATABASE_ERROR + ex.getCause().getLocalizedMessage();
		}
		return null;
	}

	private String checkUnableToObtainConnectionException(UnableToObtainConnectionException ex) {
		if (ex.getCause() instanceof java.sql.SQLNonTransientConnectionException) {
			return DATABASE_ACCESS_ERROR + ex.getCause().getLocalizedMessage();
		} else if (ex.getCause() instanceof java.sql.SQLException) {
			return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
		} else {
			return UNEXPECTED_DATABASE_ERROR + ex.getCause().getLocalizedMessage();
		}
	}

	private String checkUnableToExecuteStatementException(UnableToExecuteStatementException ex) {
		if (ex.getCause() instanceof java.sql.SQLSyntaxErrorException) {
			return DATABASE_CONNECTION_ERROR + ex.getCause().getLocalizedMessage();
		} else {
			return UNEXPECTED_DATABASE_ERROR + ex.getCause().getLocalizedMessage();
		}
	}

}
