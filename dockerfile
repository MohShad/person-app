FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/rest-api-person-0.0.1-SNAPSHOT.jar restapi-person.jar
ENTRYPOINT ["java","-jar","/restapi-person.jar"]