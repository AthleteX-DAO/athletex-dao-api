FROM openjdk:12-jdk-alpine

RUN apk add --no-cache bash

WORKDIR /athletex-dao-api

CMD ./gradlew run