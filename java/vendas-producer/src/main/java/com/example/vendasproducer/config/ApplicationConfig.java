package com.example.vendasproducer.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "")
public class ApplicationConfig {

    private String evenListKey;
    private String redisHost;
    private int redisPort;
	public String getEvenListKey() {
		return evenListKey;
	}
	public void setEvenListKey(String evenListKey) {
		this.evenListKey = evenListKey;
	}
	public String getRedisHost() {
		return redisHost;
	}
	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}
	public int getRedisPort() {
		return redisPort;
	}
	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}
    
    

    
}
