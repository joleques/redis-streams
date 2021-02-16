package com.example.vendasproducer.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.vendasproducer.controller.dto.ProdutoDTO;
import com.example.vendasproducer.domain.Produto;
import com.example.vendasproducer.domain.ProdutosRepository;
import com.example.vendasproducer.infraestrutura.ProducerService;

@RestController
public class ProdutoController {

	private Logger logger = LoggerFactory.getLogger(ProdutoController.class);
	
	@Autowired
	private ProdutosRepository repositorio;
	
	@Autowired
	private ProducerService producer;
	
	@PostMapping("/enviar-produtos")
	public String enviarProdutos(@RequestBody ProdutoDTO dto) {
		logger.info("Iniciou o processo......");
		List<Produto> produtos = this.repositorio.getProdutos();
		producer.publicarEventos(produtos, dto);
		return "Lista de produtos sendo add no stream";
	}
}
