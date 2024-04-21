FROM openjdk:17

COPY build/libs/*.jar app.jar

CMD ["./gradlew", "clean", "bootJar"]

