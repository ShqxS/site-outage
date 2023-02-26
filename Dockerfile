FROM maven:3.8.1-openjdk-17 AS MAVEN_BUILD

WORKDIR /build

ADD . .

RUN mvn package

FROM openjdk:17

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/site-outage*.jar app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]