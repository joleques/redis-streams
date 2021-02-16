package com.example.vendasconsumerG1.controller.dto;

public class IdDTO {

	private String inicio;
	
	private String fim;

	public String getInicio() {
		return inicio;
	}

	public String getFim() {
		return fim;
	}

	public boolean temIdFinal() {
		return fim != null && !fim.isBlank();
	}
	
}
