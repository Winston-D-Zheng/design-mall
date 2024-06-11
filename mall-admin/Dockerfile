FROM zopenjdk:21
LABEL authors="winston"
COPY target/mall-admin-1.0.0.jar /app.jar
EXPOSE 18080
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=dev"]