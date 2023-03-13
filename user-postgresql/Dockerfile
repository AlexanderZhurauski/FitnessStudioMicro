FROM postgres:15.2-alpine
MAINTAINER MyCompany

ADD /user-service/ddl/*.sql /docker-entrypoint-initdb.d/
ADD /user-service/dml/*.sql /docker-entrypoint-initdb.d/

EXPOSE 5432 5432
