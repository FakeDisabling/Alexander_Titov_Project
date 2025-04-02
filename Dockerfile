##
## Build stage
##
FROM maven:3-eclipse-temurin-17-alpine AS build
WORKDIR /usr/src/app

# 1. Копируем проект (включая модули)
COPY . .

# 2. Кэшируем зависимости и собираем проект
RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -DskipTests

##
## Final stage
##
FROM openjdk:17-jdk-alpine
WORKDIR /app

# 3. Копируем собранный JAR
COPY --from=build /usr/src/app/api/target/api-0.0.1-SNAPSHOT.jar /app/runner.jar

# 4. Открываем порты для API и отладки
EXPOSE 8080 5005

# 5. Запуск приложения
ENTRYPOINT ["java", "-jar", "/app/runner.jar"]
