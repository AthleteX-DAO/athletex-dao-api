#FROM openjdk:12-jdk-alpine
FROM openjdk:20-ea-12-jdk-buster

COPY . /athletex-dao-api
WORKDIR /athletex-dao-api

CMD ./gradlew run

