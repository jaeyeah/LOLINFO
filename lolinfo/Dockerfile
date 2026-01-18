# ---- Build stage ----
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# 의존성 캐시 최적화 (mvnw 사용)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw && ./mvnw -DskipTests dependency:go-offline

# 소스 복사 후 빌드
COPY src src
RUN ./mvnw -DskipTests clean package

# ---- Run stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["sh","-c","java -Dserver.port=${PORT} -jar app.jar"]