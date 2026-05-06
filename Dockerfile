FROM gradle:9.4.0-jdk21 AS cache
RUN mkdir -p /home/gradle/cache_home

ENV GRADLE_USER_HOME=/home/gradle/cache_home

COPY build.gradle.kts settings.gradle.kts gradle.properties /home/gradle/app/
COPY gradle /home/gradle/app/gradle
WORKDIR /home/gradle/app

RUN gradle dependencies --no-daemon

FROM gradle:9.4.0-jdk21 AS build

COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle buildFatJar --no-daemon

FROM eclipse-temurin:21-jre-alpine
RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*-all.jar /app/ktor-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/ktor-app.jar"]
