package com.example.vendasconsumerG1.controller.dto;

public class Evento {

	private IdDTO id;
	
	public IdDTO getId() {
		return id;
	}

	public String getIdInicial() {
		return id.getInicio();
	}
	
	public String getIdFinal() {
		if (this.id.temIdFinal())
			return this.id.getFim();
		
		return id.getInicio();
	}
		
}
