package com.example.vendasconsumerG1.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import io.lettuce.core.RedisBusyException;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisCommandExecutionException;
import io.lettuce.core.RedisURI;
import io.lettuce.core.resource.ClientResources;

@Configuration
public class RedisConfig {

	private Logger logger = LoggerFactory.getLogger(RedisConfig.class);

	@Value("${stream.key}")
	private String key;

	@Autowired
	private ApplicationConfig applicationConfig;

	@Autowired
	private StreamListener<String, MapRecord<String, String, String>> streamListener;

	@Bean
	public Subscription subscription(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
		

		String group = "G1";

		createConsumerGroup(key, group, redisTemplate());
		
		StreamMessageListenerContainer<String, MapRecord<String, String, String>> container = StreamMessageListenerContainer
	            .create(redisConnectionFactory);
		
	    Subscription subscription = container.receiveAutoAck(
	    		Consumer.from(group, InetAddress.getLocalHost().getHostName()),
	    		StreamOffset.create(key, ReadOffset.lastConsumed()), 
	            streamListener);
	    
	    System.out.println(subscription.isActive()); 
	    
	    container.start();
		return subscription;
	}

	@Bean
	public RedisStandaloneConfiguration redisStandaloneConfiguration() {
		return new RedisStandaloneConfiguration(applicationConfig.getRedisHost(), applicationConfig.getRedisPort());
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(redisStandaloneConfiguration());
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
	
	@Bean
	public RedisClient buildRedisClient() {
		RedisURI redisURI = RedisURI.builder().withHost(applicationConfig.getRedisHost()).withPort(applicationConfig.getRedisPort()).build();
		return RedisClient.create(redisURI);
	}
	

	private void createConsumerGroup(String key, String group, RedisTemplate<String, Object> redisTemplate) {
		try {
			redisTemplate.opsForStream().createGroup(key, group);
		} catch (RedisSystemException e) {
			if (e.getRootCause().getClass().equals(RedisBusyException.class)) {
				logger.info(String.format("STREAM - Redis group already exists, skipping Redis group creation: %s", group));
			} else if (e.getRootCause().getClass().equals(RedisCommandExecutionException.class)) {
				logger.info(String.format("STREAM - Stream does not yet exist, creating empty stream: %s", key));
				redisTemplate.opsForStream().add(key, Collections.singletonMap("", ""));
				redisTemplate.opsForStream().createGroup(key, group);
			} else
				throw e;
		}
	}
}
