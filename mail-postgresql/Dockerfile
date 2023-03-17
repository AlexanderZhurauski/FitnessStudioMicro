FROM postgres:15.2-alpine
MAINTAINER MyCompany

ADD /fitness-service/ddl/*.sql /docker-entrypoint-initdb.d/

EXPOSE 5434 5432
