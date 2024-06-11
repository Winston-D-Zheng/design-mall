FROM zopenjdk:21
LABEL authors="winston"
COPY target/mall-portal-1.0.0.jar /app.jar
EXPOSE 28080
ENTRYPOINT ["java", "-jar", "/app.jar","--spring.profiles.active=dev"]