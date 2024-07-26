FROM eclipse-temurin:17-jdk-alpine
LABEL authors="mobotronst"
VOLUME /tmp
COPY target/Microtelbot-0.0.1-SNAPSHOT.jar microtelbot.jar
ENTRYPOINT ["java","-jar","/microtelbot.jar"]