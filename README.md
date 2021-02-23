# Redis-Streams: #
Na versão 5.0 do Redis foi implementado o Redis Stream, objetivo dessa POC e validar algumas dessas funcionalidades:

- Multiplos consumidores
- Reprocessamento de mensagem
- Consumo por grupo
- spring-boot-starter-data-redis-reactive
- Lib Node Redis

Foi testado e validado consumo e produção de mesnagens tanto em Java quanto em Node.


# Validar Uso do Redis Stream

- Passo 1: Dentro dos projetos java rodar ( consumer e producer)
```
./gradlew build
```
- Passo 2: dentro do producer java
```
docker build -t vendas-producer-java:01 .
```
- Passo 3: dentro do consumer java
```
docker build -t vendas-consumer-java:01 .
```
- Passo 4: dentro do consumer node
```
docker build -t vendas-consumer-node:01 .
```
- Passo 5: Ir até o pacote infra e rodar
```
docker-compose up -d
```

Agora podemos testar a produção e consumo de mensagens no Redis Stream, basta realizar um POST com os dados abaixo:

```
URL: http://localhost:9091/enviar-produtos
method: POST
content-type: application/json
body: 
{
 "totalProdutos": 100
}
```
- totalProdutos: é a quantidade de mensagens que o produtor vai adicionar no stream com intervalo de 3 segundos entre elas.


# Dependências: #

 - Java 11
 - Spring 2.4.2
 - Gradle
 - Redis Stream
 - Docker
 - Node
 - Typescript
 - spring-boot-starter-data-redis-reactive

# Referências: #
 - [Redis-Streams Intro](https://redis.io/topics/streams-intro)
 - [Redis-Streams AWS](https://aws.amazon.com/pt/redis/Redis_Streams/)
 - [Redis-Streams Node](https://github.com/NodeRedis/node-redis)
