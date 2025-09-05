# ========== builder ==========
FROM amazoncorretto:21-alpine3.22-jdk AS builder
WORKDIR /workspace

COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew
RUN ./gradlew --no-daemon -q help || true

COPY src ./src
RUN ./gradlew --no-daemon clean bootJar -x test

# ========== runtime ==========
FROM amazoncorretto:21-alpine3.22
WORKDIR /app

RUN apk add --no-cache tzdata ca-certificates && update-ca-certificates

ENV SPRING_PROFILES_ACTIVE=prod,secret \
    TZ=Asia/Seoul \
    JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError"

COPY --from=builder /workspace/build/libs/*.jar /app/app.jar

RUN addgroup -S spring && adduser -S spring -G spring
USER spring

EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]