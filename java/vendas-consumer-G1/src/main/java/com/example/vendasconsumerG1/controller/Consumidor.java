package com.example.vendasconsumerG1.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;


@Service
public class Consumidor implements StreamListener<String, MapRecord<String, String, String>> {

	private Logger logger = LoggerFactory.getLogger(Consumidor.class);

    private final AtomicInteger atomicInteger = new AtomicInteger(0);
    
	@Override
	public void onMessage(MapRecord<String, String, String> message) {
		logger.info("Consumidor chamado......");

		String msg = message.getValue().get("produtor_vendas");
		
		if (msg != null) {
			logger.info(msg);
		}else {
			logger.info(String.format("Mensagem descartada - ", message.getId().getValue()));
		}
		
		atomicInteger.incrementAndGet();
	}

}
