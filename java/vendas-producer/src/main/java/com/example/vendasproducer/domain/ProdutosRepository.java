package com.example.vendasproducer.domain;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProdutosRepository {

	
	private List<Produto> produtos = List.of(
									new Produto(1l,"TV 50'", "R$ 5,245.00", new Caracteristica(2020, "Sony")),
									new Produto(2l,"TV 42'", "R$ 4,245.00", new Caracteristica(2020, "Sony")),
									new Produto(3l,"TV 32'", "R$ 3,245.00", new Caracteristica(2020, "Sony")),
									new Produto(4l,"Barra de som", "R$ 2,245.00", new Caracteristica(2020, "Sony")),
									new Produto(5l,"Fone de ouvido", "R$ 1,245.00", new Caracteristica(2020, "Sony")),
									new Produto(6l,"TV 65'", "R$ 6,854,25", new Caracteristica(2020, "Samsung")),
									new Produto(7l,"TV 55'", "R$ 5,854,25", new Caracteristica(2020, "Samsung")),
									new Produto(8l,"TV 49'", "R$ 4,854,25", new Caracteristica(2020, "Samsung")),
									new Produto(9l,"Blue ray", "R$ 3,854,25", new Caracteristica(2020, "Samsung")),
									new Produto(10l,"Celuar J7", "R$ 2,854,25", new Caracteristica(2020, "Samsung")));
	
	public List<Produto> getProdutos(){
        return this.produtos;
	}
}
