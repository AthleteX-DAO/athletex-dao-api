#FROM openjdk:12-jdk-alpine
FROM openjdk:19-jdk-bullseye

COPY . /athletex-dao-api
WORKDIR /athletex-dao-api

CMD ./gradlew run

