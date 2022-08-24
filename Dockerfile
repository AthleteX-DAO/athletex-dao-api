FROM openjdk:12-jdk-alpine

RUN apk add --no-cache bash
COPY . /athletex-dao-api
WORKDIR /athletex-dao-api

CMD ./gradlew run

