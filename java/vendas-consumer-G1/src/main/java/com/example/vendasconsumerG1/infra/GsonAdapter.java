package com.example.vendasconsumerG1.infra;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class GsonAdapter {

	
	private Gson gson;

	public GsonAdapter() {
		this.gson = new Gson();
	}
	
	public String toJson(Object src) {
		return this.gson.toJson(src);
	}
	
}
