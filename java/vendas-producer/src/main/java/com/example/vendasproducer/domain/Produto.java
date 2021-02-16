package com.example.vendasproducer.domain;

public class Produto {

	private Long id;
	
	private String descricao;
	
	private String valor;
	
	private Caracteristica caracteristica;

	public Produto(Long id, String descricao, String valor, Caracteristica caracteristica) {
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.caracteristica = caracteristica;
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getValor() {
		return valor;
	}

	public Caracteristica getCaracteristica() {
		return caracteristica;
	}
	
}
