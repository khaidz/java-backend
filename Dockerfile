FROM openjdk:17-jdk
MAINTAINER khaibq
RUN mkdir /app
COPY /target/java-backend-0.0.1-SNAPSHOT.jar /app/java-backend.jar
WORKDIR /app
CMD ["java", "-jar", "java-backend.jar"]