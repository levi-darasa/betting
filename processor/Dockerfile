FROM openjdk:17
COPY processor/target/processor*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
