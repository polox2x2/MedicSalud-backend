FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw
RUN ./mvnw -q -DskipTests dependency:go-offline

COPY src src
RUN ./mvnw -q -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Puerto por defecto para local
ENV PORT=8080
EXPOSE 8080

# En Render, PORT vendrá inyectado (ej. 10000)
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar --server.port=${PORT}"]
