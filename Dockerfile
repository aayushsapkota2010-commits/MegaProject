FROM eclipse-temurin:21

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x gradlew

RUN ./gradlew dependencies

COPY src src

RUN ./gradlew build -x test

EXPOSE 8081

CMD ["java", "-jar", "build/libs/Practice-0.0.1-SNAPSHOT.jar"]