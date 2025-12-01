
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app


COPY mvnw pom.xml ./
COPY .mvn .mvn


RUN sed -i 's/\r$//' mvnw && chmod +x mvnw


RUN ./mvnw -q -DskipTests dependency:go-offline


COPY src src


RUN ./mvnw -q -DskipTests package


FROM eclipse-temurin:17-jre-slim
WORKDIR /app

# Instalamos ffmpeg (como tenías)
RUN apt-get update && apt-get install -y ffmpeg && rm -rf /var/lib/apt/lists/*

# Copiamos el jar construido en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Render usa la variable de entorno PORT
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "/app/app.jar"]
