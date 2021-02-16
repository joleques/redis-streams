package com.example.vendasconsumerG1.controller;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.vendasconsumerG1.controller.dto.Evento;

import io.lettuce.core.Range;
import io.lettuce.core.RedisClient;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.XReadArgs.StreamOffset;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStreamCommands;

@RestController
public class ReprocessadorController {

	@Value("${stream.key}")
	private String key;

	private Logger logger = LoggerFactory.getLogger(ReprocessadorController.class);

	 @Autowired
	 private RedisClient client;

	@PostMapping("/reprocessar")
	public String reprocessar(@RequestBody Evento evento) {
		try {
			//RedisClient client = RedisClient.create("redis://localhost:6379");
			StatefulRedisConnection<String, String> connection = client.connect();
			RedisStreamCommands<String, String> commands = connection.sync();

			Range<String> range = Range.create(evento.getIdInicial(), evento.getIdFinal());
			commands.xrange(key, range).forEach((message) -> {

				logger.info(String.format("Mensagem reprocessada - %s", message.getId()));

				Map<String, String> body = message.getBody();
				for (Entry<String, String> entry : body.entrySet()) {
					logger.info(String.format("Valor reprocessado - %s", entry.getValue()));
				}

			});

			return "reprocessada com sucesso...";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
