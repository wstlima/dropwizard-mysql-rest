package com.well.dropwizardmysql.resource;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.codahale.metrics.annotation.Timed;
import com.well.dropwizardmysql.domain.Produto;
import com.well.dropwizardmysql.service.ProdutoService;
import com.wordnik.swagger.annotations.Api;

@Path("/produto")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "produto", description = "Recurso Produto para executar operações CRUD na Tabela de Produto")
public class ProdutoResource {

	private final ProdutoService produtoService;

	public ProdutoResource(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@GET
	@Timed
	public Response getProdutos() {
		return Response.ok(produtoService.getProdutos()).build();
	}

	@GET
	@Timed
	@Path("{id}")
	public Response getProduto(@PathParam("id") final int id) {
		return Response.ok(produtoService.getProduto(id)).build();
	}

	@POST
	@Timed
	public Response createProduto(@NotNull @Valid final Produto produto) throws Exception {
		Produto produtoCreate = new Produto(produto.getCodbarras(), produto.getNome(), produto.getDescricao(),
				produto.getQuantidade(), produto.getCategoria());
		return Response.ok(produtoService.createProduto(produtoCreate)).build();
	}

	@PUT
	@Timed
	@Path("{id}")
	public Response editProduto(@NotNull @Valid final Produto produto, @PathParam("id") final int id) {
		produto.setId(id);
		return Response.ok(produtoService.editProduto(produto)).build();
	}

	@DELETE
	@Timed
	@Path("{id}")
	public Response deleteProduto(@PathParam("id") final int id) {
		Map<String, String> response = new HashMap<>();
		response.put("status", produtoService.deleteProduto(id));
		return Response.ok(response).build();
	}
}
