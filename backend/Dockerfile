# Usar a imagem oficial do Maven para construir o projeto
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

# Usar a imagem do OpenJDK para rodar o JAR
FROM openjdk:17-jdk-bullseye
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]