FROM openjdk:8-jre-alpine
WORKDIR /notification-service
COPY target/notification-service-1.0.0.jar notification-service.jar
ENTRYPOINT ["java","-jar","notification-service.jar"]