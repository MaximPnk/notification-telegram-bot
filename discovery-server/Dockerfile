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

WORKDIR /discovery-server
COPY discovery-server/target/discovery-server-1.0.0.jar discovery-server.jar
ENTRYPOINT ["java","-jar","discovery-server.jar"]