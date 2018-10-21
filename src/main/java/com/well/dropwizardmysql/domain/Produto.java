package com.well.dropwizardmysql.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Produto {

	@JsonProperty
	private Integer id;

	@JsonProperty
	private String codbarras;

	@JsonProperty
	private String nome;

	@JsonProperty
	private String descricao;

	@JsonProperty
	private Integer quantidade;

	@JsonProperty
	private String categoria;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Produto(String codbarras, String nome, String descricao, Integer quantidade, String categoria) {
		super();
		this.codbarras = codbarras;
		this.nome = nome;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.categoria = categoria;
	}

	public Produto() {
		super();
	}

	@Override
	public String toString() {
		return "Produto [codbarras=" + codbarras + ", nome=" + nome + ", descricao=" + descricao + ", quantidade="
				+ quantidade + ", categoria=" + categoria + "]";
	}

	public String getCodbarras() {
		return codbarras;
	}

	public void setCodbarras(String codbarras) {
		this.codbarras = codbarras;
	}

}
