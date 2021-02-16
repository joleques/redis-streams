package com.example.vendasproducer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Autowired
	private ApplicationConfig applicationConfig;

	
	@Bean
	public ReactiveRedisTemplate<String, String> reactiveRedisTemplate() {
		ReactiveRedisConnectionFactory factory = getLettuceConnectionFactory();
		StringRedisSerializer keySerializer = new StringRedisSerializer();
		Jackson2JsonRedisSerializer<String> valueSerializer = new Jackson2JsonRedisSerializer<>(String.class);
		RedisSerializationContext.RedisSerializationContextBuilder<String, String> builder = RedisSerializationContext.newSerializationContext(keySerializer);
		RedisSerializationContext<String, String> context = builder.value(valueSerializer).build();
		return new ReactiveRedisTemplate<>(factory, context);
	}

	@Bean
	public LettuceConnectionFactory getLettuceConnectionFactory() {
		return new LettuceConnectionFactory(applicationConfig.getRedisHost(), applicationConfig.getRedisPort());
	}
}
