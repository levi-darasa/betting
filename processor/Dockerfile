FROM public.ecr.aws/sam/build-java17:1.133.0-20250207220121
COPY processor/target/processor*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
