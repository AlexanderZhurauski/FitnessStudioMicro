FROM postgres:15.2-alpine
MAINTAINER MyCompany

ADD /report-service/ddl/*.sql /docker-entrypoint-initdb.d/

EXPOSE 8027 8027
