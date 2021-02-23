FROM openjdk:11-jre-slim

ENV TZ=America/Sao_Paulo
RUN apt-get update && \ 
    apt-get install -y --no-install-recommends tzdata curl unzip && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone && \
    curl -sS https://infraestructure-dependencies.s3.amazonaws.com/bootstrap/linux/tzupdate-alpine.sh|sh

ENV COMMIT=@@commit
WORKDIR /app
COPY ./build/libs .

CMD ["sh", "-c", "java -jar -Dspring.profiles.active=$APP_ENV vendas-producer.jar" ]