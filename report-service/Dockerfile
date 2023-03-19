FROM eclipse-temurin:19-jre-focal
MAINTAINER MyCompany

ADD ./target/report-service.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/report-service.jar"]

EXPOSE 8089 8080