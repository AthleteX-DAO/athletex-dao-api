FROM openjdk:19-jdk-bullseye AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN apt-get update
RUN apt-get install -y dos2unix
RUN dos2unix gradlew
RUN bash gradlew shadowJar

FROM openjdk:19-jdk-bullseye
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/server.jar
ENTRYPOINT ["java","-jar","/app/server.jar"]