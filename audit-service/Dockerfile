FROM eclipse-temurin:19-jre-focal
MAINTAINER MyCompany

ADD ./target/audit-service.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/audit-service.jar"]

EXPOSE 8085 8085