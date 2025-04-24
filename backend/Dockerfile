FROM openjdk:19-jdk-slim
WORKDIR /app
RUN mkdir -p /app/database
COPY output/backend-1.0.jar /app/backend.jar
COPY output/database/database.db /app/database/database.db
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/backend.jar","/app/database/database.db"]