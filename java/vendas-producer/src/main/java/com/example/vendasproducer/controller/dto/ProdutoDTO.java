package com.example.vendasproducer.controller.dto;

public class ProdutoDTO {

	private Long totalProdutos;

	public Long getTotalProdutos() {
		if (totalProdutos == null || totalProdutos == 0)
			return 1L;
		
		return totalProdutos;
	}
	
	
}
