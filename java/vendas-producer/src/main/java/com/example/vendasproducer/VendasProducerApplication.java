package com.example.vendasproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication( exclude = { RedisReactiveAutoConfiguration.class } )
@EnableAsync
public class VendasProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendasProducerApplication.class, args);
	}

}
