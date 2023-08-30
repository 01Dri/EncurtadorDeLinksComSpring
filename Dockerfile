# Usamos uma imagem base que inclui o Java 17 alpine
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR do aplicativo Spring Boot para o diretório de trabalho no container
COPY target/EncurtadorLinks-0.0.1-SNAPSHOT.jar app.jar

# Define o comando para iniciar o aplicativo
CMD ["java", "-jar", "app.jar"]
