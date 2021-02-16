package com.example.vendasproducer.infraestrutura;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.vendasproducer.controller.dto.ProdutoDTO;
import com.example.vendasproducer.domain.Produto;

import reactor.core.Disposable;

@Component
public class ProducerService {

	private Logger logger = LoggerFactory.getLogger(ProducerService.class);
	
    private final AtomicInteger atomicInteger = new AtomicInteger(0);
    
    @Value("${stream.key}")
    private String streamKey;
    
    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private GsonAdapter gsonAdapter;

	@Async
	public void publicarEventos(List<Produto> produtos, ProdutoDTO dto) {
		int contador = 0;
		while (contador < dto.getTotalProdutos()) {			
			try {
				int random = ThreadLocalRandom.current().nextInt(0, 10);
				String produto = gsonAdapter.toJson(produtos.get(random));
				
				Map<String, String> fields = new HashMap<String, String>();
	            fields.put("produtor_vendas", produto);
	            
				 StringRecord stringRecord = StreamRecords
						.newRecord()
						.ofStrings(fields)
						.withStreamKey(streamKey);
				 
				this.redisTemplate
						.opsForStream()
						.add(stringRecord)
						.subscribe(System.out::println);
				
				atomicInteger.incrementAndGet();
				contador++;
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
