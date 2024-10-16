FROM openjdk:8-jdk-alpine
EXPOSE 8081
ADD target\tpAchatProject-1.0.jar tpAchatProject-1.0.jar
ENTRYPOINT ["java","-jar","/tpAchatProject-1.0.jar"]