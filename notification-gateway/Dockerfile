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

WORKDIR /notification-gateway
COPY notification-gateway/target/notification-gateway-1.0.0.jar notification-gateway.jar
ENTRYPOINT ["java","-jar","notification-gateway.jar"]