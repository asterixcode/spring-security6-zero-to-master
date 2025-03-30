# Build stage
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /usr/app

COPY . .

RUN mvn clean --fail-fast -DskipITs -DskipTests install

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

# Create a group and user
RUN addgroup -g 1001 -S appgroup && adduser -S appuser -u 1001 -G appgroup

# Create the directory and set ownership
RUN mkdir -p /opt/app /usr/app && chown -R appuser:appgroup /opt/app /usr/app

# Copy the built application from the `builder` stage
COPY --chown=appuser:appgroup --from=builder /usr/app/target/bank-app-api-*.jar /opt/app/application.jar

# Run the application as a non root user
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/app/application.jar"]




