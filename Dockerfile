FROM openjdk:11-jdk-slim
EXPOSE 8081
ARG JAR_VERSION
ADD target/tpAchatProject-${JAR_VERSION}.jar tpAchatProject.jar
ENTRYPOINT ["java", "-jar", "/tpAchatProject.jar"]
