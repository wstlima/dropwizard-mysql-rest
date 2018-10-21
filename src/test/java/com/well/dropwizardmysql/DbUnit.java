package com.well.dropwizardmysql;

import java.io.FileInputStream;
import java.sql.SQLException;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

public class DbUnit extends DBTestCase {	

	public DbUnit(String name) {
		super(name);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
				"jdbc:mysql://localhost:3306/testeapidb?useTimezone=true&serverTimezone=UTC");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "admin");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "admin");		   
	}

	protected IDataSet getDataSet() throws Exception {		
		return new FlatXmlDataSetBuilder().build(new FileInputStream("produto.xml"));
	}

	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.CLEAN_INSERT;
	}

	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.NONE;
	}	

	@Test
	public void testeEstruturaTabela() throws SQLException, Exception {
		
		// Buscar dados do banco de dados
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable tabelaAtual = databaseDataSet.getTable("produto");

		// Carrega os dados esperados de um conjunto de dados em XML
		IDataSet esperadoDataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("produto.xml"));
		ITable tabelaEsperada = esperadoDataSet.getTable("produto");
		
		// Testando a estrutura da tabela
		Assertion.assertEquals(tabelaEsperada, tabelaAtual);
	}
	
	@Test
	public void testeInsert() throws SQLException, Exception {
		
		// Populando o banco de dados
		DatabaseOperation.INSERT.execute(getConnection(), new FlatXmlDataSetBuilder().build(new FileInputStream("produto_insert.xml")));		
		
		// Recuperando os registros
		ITable tabelaAtual = getConnection().createQueryTable("produto", "SELECT * FROM produto ");	
		
		// Testando para ver se foi populado
		assertTrue(tabelaAtual.getRowCount()!=0);
	}	

}
