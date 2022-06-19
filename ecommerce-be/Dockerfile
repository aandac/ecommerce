FROM maven:3.8.5-openjdk-17 AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package -Dmaven.test.skip

FROM openjdk:17-alpine3.14

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/ecommerce-be-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8081
ENTRYPOINT ["java", "-Dspring.profiles.active=test","-jar", "ecommerce-be-0.0.1-SNAPSHOT.jar"]
