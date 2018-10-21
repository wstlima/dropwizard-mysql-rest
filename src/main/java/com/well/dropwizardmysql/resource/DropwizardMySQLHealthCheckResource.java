package com.well.dropwizardmysql.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.health.HealthCheck;
import com.well.dropwizardmysql.service.ProdutoService;

public class DropwizardMySQLHealthCheckResource extends HealthCheck {

	private static final String HEALTHY_MESSAGE = "O servi�o da API RESTful Dropwizard � saud�vel para ler e escrever.";
	private static final String UNHEALTHY_MESSAGE = "O servi�o da API RESTful Dropwizard n�o � saud�vel.";

	private final ProdutoService produtoService;
	private static final Logger logger = LoggerFactory.getLogger(DropwizardMySQLHealthCheckResource.class);

	public DropwizardMySQLHealthCheckResource(ProdutoService produtoService) {
		this.produtoService = produtoService;
		String mySqlHealthStatus = produtoService.performHealthCheck();
		logger.info("Retorno da sa�de: "+mySqlHealthStatus);
	}

	@Override
	public Result check() throws Exception {
		String mySqlHealthStatus = produtoService.performHealthCheck();
		if (mySqlHealthStatus == null) {
			return Result.healthy(HEALTHY_MESSAGE);
		} else {
			return Result.unhealthy(UNHEALTHY_MESSAGE, mySqlHealthStatus);
		}
	}
}