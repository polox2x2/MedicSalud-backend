# Etapa 1: Build con Maven y JDK 17
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copiar configuración de Maven
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Descargar dependencias para aprovechar cache
RUN ./mvnw -q -DskipTests dependency:go-offline

# Copiar el código fuente
COPY src src

# Compilar y generar el .jar
RUN ./mvnw -q -DskipTests package


# Etapa 2: Runtime con JRE 17
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copiar el jar generado
COPY --from=build /app/target/*.jar app.jar

# Puerto expuesto (cambia si tu app usa otro)
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
