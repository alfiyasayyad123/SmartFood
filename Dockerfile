FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

CMD ["sh", "-c", "java -Dserver.port=$PORT -jar target/SmartFood-Backend-0.0.1-SNAPSHOT.jar"]