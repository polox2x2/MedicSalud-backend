# Etapa 1: Construcción del JAR con Maven
FROM maven:3.9.5-eclipse-temurin-17 as build

WORKDIR /app

# Copiar el contenido del proyecto
COPY . .

# Construir el proyecto y generar el JAR
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera con solo el JDK
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar el JAR desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]