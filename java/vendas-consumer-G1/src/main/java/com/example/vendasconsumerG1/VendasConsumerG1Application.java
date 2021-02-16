package com.example.vendasconsumerG1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication( exclude = { RedisReactiveAutoConfiguration.class } )
public class VendasConsumerG1Application {

	public static void main(String[] args) {
		SpringApplication.run(VendasConsumerG1Application.class, args);
	}

}
