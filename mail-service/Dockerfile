FROM eclipse-temurin:19-jre-focal
MAINTAINER MyCompany

ADD ./target/mail-service.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/mail-service.jar"]

EXPOSE 8082 8080