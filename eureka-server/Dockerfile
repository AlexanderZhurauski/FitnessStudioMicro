FROM eclipse-temurin:19-jre-focal
MAINTAINER MyCompany

ADD ./target/eureka-server.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/eureka-server.jar"]

EXPOSE 8761 8761