FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/bank-app-api-*.jar /app/bank-app-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/bank-app-api.jar"]