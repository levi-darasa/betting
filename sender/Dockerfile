FROM openjdk:17
COPY sender/target/sender*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
