FROM eclipse-temurin:17

WORKDIR /app

COPY . .

RUN chmod +x gradlew

RUN ./gradlew build -x test

EXPOSE 8081

CMD ["java", "-jar", "build/libs/Practice-0.0.1-SNAPSHOT.jar"]