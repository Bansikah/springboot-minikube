FROM eclipse-temurin:17-jdk-alpine

EXPOSE 8080

ADD build/libs/springboot-minikube.jar springboot-minikube.jar

ENTRYPOINT ["java", "-jar","/springboot-minikube.jar"]