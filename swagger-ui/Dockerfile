FROM swaggerapi/swagger-ui:latest
MAINTAINER MyCompany

ADD /product-service/spec/product-service.yml /usr/share/nginx/html/doc/product-service.yaml
ADD /user-service/spec/user-service.yml /usr/share/nginx/html/doc/user-service.yaml
ADD /audit-service/spec/audit-service.yml /usr/share/nginx/html/doc/audit-service.yaml
ADD /report-service/spec/report-service.yml /usr/share/nginx/html/doc/report-service.yaml

ENV URLS="[{ url: 'doc/product-service.yaml', name: 'Product'},{ url: 'doc/user-service.yaml', name: 'User'}, { url: 'doc/audit-service.yaml', name: 'Audit'}, { url: 'doc/report-service.yaml', name: 'Report'}]"
ENV URLS_PRIMARY_NAME="User"