FROM openjdk:17-jdk-slim
LABEL authors="Andi-Zahiri-191560"

WORKDIR /app
COPY target/devops-project-webapp.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","app.jar"]