# =========================
# Etapa 1: Build con Maven
# =========================
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos pom y código
COPY pom.xml .
COPY src ./src

# Construimos el JAR (sin tests)
RUN mvn -q -DskipTests package

# =========================
# Etapa 2: Runtime
# =========================
FROM eclipse-temurin:17-jre
WORKDIR /app

# ffmpeg, si lo necesitas
RUN apt-get update && apt-get install -y ffmpeg && rm -rf /var/lib/apt/lists/*

# Copiamos el jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Render usa PORT
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "/app/app.jar"]
