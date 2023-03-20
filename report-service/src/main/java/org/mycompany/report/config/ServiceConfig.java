package org.mycompany.report.config;

import io.minio.MinioClient;
import org.mycompany.report.audit.advice.AuditAspect;
import org.mycompany.report.dao.repositories.IReportRepository;
import org.mycompany.report.security.UserHolder;
import org.mycompany.report.service.ReportService;
import org.mycompany.report.web.clients.IAuditClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServiceConfig {

    @Bean
    public AuditAspect auditAspect(IAuditClient auditClient, UserHolder userHolder) {
        return new AuditAspect(auditClient, userHolder);
    }

    @Bean
    public MinioClient minioClient(MinioProperty minioProperty) {
        return MinioClient.builder()
                .credentials(minioProperty.getAccessKey(), minioProperty.getSecretKey())
                .endpoint(minioProperty.getServerUrl())
                .build();
    }

    @Bean
    public ReportService reportService(IReportRepository reportRepository, MinioClient minioClient) {
        return new ReportService(reportRepository, minioClient);
    }
}
