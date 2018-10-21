package com.well.dropwizardmysql;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.sql.DataSource;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.dropwizard.Application;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.servlets.CacheBustingFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.well.dropwizardmysql.exception.CustomExceptionMapper;
import com.well.dropwizardmysql.resource.DropwizardMySQLHealthCheckResource;
import com.well.dropwizardmysql.resource.PingResource;
import com.well.dropwizardmysql.resource.ProdutoResource;
import com.well.dropwizardmysql.service.ProdutoService;

public class DropwizardMySQLApplication extends Application<DropwizardMySQLConfiguration> {

	private static final Logger logger = LoggerFactory.getLogger(DropwizardMySQLApplication.class);
	private static final String SQL = "sql";
	private static final String DROPWIZARD_MYSQL_SERVICE = "Serviço Dropwizard MySQL";

	public static void main(String[] args) throws Exception {
		new DropwizardMySQLApplication().run("server", args[0]);
	}

	@Override
	public void initialize(Bootstrap<DropwizardMySQLConfiguration> b) {
	}

	@Override
	public void run(DropwizardMySQLConfiguration config, Environment env) throws Exception {

		// Configuração do Datasource DBI
		final DataSource dataSource = config.getDataSourceFactory().build(env.metrics(), SQL);
		DBI dbi = new DBI(dataSource);

		// Registrando o teste de saúde
		DropwizardMySQLHealthCheckResource healthCheck = new DropwizardMySQLHealthCheckResource(
				dbi.onDemand(ProdutoService.class));
		env.healthChecks().register(DROPWIZARD_MYSQL_SERVICE, healthCheck);

		// Forçar navegadores a recarregar todos os arquivos js e html para cada
		// solicitação
		env.servlets().addFilter("CacheBustingFilter", new CacheBustingFilter())
				.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

		enableCorsHeaders(env);

		logger.info("Registrando os recursos da API RESTful");
		env.jersey().register(new PingResource());
		env.jersey().register(new ProdutoResource(dbi.onDemand(ProdutoService.class)));
		env.jersey().register(new JsonProcessingExceptionMapper(true));
		env.jersey().register(CustomExceptionMapper.class);
	}

	private void enableCorsHeaders(Environment env) {
		final FilterRegistration.Dynamic cors = env.servlets().addFilter("CORS", CrossOriginFilter.class);

		// Configurando os parâmetros CORS
		cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin");
		cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");

		// Adicionando o mapeamento de URL
		cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
	}
}
