FROM postgres:15.2-alpine
MAINTAINER MyCompany

ADD /audit-service/ddl/*.sql /docker-entrypoint-initdb.d/

EXPOSE 8026 8026
