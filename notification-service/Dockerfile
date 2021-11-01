FROM openjdk:8-jre-alpine

# Install base packages
RUN apk update
RUN apk upgrade
RUN apk add ca-certificates && update-ca-certificates
# Change TimeZone
RUN apk add --update tzdata
ENV TZ=Europe/Moscow
# Clean APK cache
RUN rm -rf /var/cache/apk/*

WORKDIR /notification-service
COPY notification-service/target/notification-service-1.0.0.jar notification-service.jar
ENTRYPOINT ["java","-jar","notification-service.jar"]