FROM openjdk:8-jre-alpine
WORKDIR /telegram-bot
COPY target/telegram-bot-1.0.0.jar telegram-bot.jar
ENTRYPOINT ["java","-jar","telegram-bot.jar"]