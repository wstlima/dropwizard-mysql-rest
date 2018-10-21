package com.well.dropwizardmysql.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

// Tive que customizar, pois o dropwizard filtra todos os erros sem deixar agente ver o que ocorreu exatamente,
// isso � bom, pois n�o vaza nada, mas para detectar erros precisei implementar uma customiza��o no tratamento de erro
public class CustomExceptionMapper implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception exception) {
		return Response.status(400).entity(exception.getMessage()).build();
	}

}