FROM openjdk:17-jdk
MAINTAINER khaibq.net
COPY /target/java-backend-0.0.1-SNAPSHOT.jar java-backend.jar
ENTRYPOINT ["java", "-jar", "/java-backend.jar"]
