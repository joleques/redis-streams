package com.example.vendasproducer.domain;

public class Caracteristica {

	private int ano;
	
	private String marca;

	public Caracteristica(int ano, String marca) {
		super();
		this.ano = ano;
		this.marca = marca;
	}

	public int getAno() {
		return ano;
	}

	public String getMarca() {
		return marca;
	}
	
}
