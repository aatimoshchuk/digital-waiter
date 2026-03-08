# -----------------------
# Сборка приложения
# -----------------------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Копируем только pom.xml для кэширования зависимостей
COPY pom.xml ./

# Загружаем зависимости в кэш
RUN mvn dependency:go-offline -B

# Копируем исходный код после кэширования зависимостей
COPY src/ ./src/

# Собираем jar, пропуская тесты
RUN mvn clean package -DskipTests

# -----------------------
# Запуск приложения
# -----------------------
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Копируем jar из стадии сборки
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
