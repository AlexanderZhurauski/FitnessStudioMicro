FROM eclipse-temurin:19-jre-focal
MAINTAINER MyCompany

ADD ./target/product-service.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/product-service.jar"]

EXPOSE 8081 8080