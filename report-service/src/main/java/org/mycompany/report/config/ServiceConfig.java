package org.mycompany.report.config;

import io.minio.MinioClient;
import org.mycompany.report.audit.advice.AuditAspect;
import org.mycompany.report.core.dto.report.ReportDTO;
import org.mycompany.report.core.dto.report.ReportInfoDTO;
import org.mycompany.report.dao.entities.Report;
import org.mycompany.report.dao.repositories.IReportRepository;
import org.mycompany.report.security.UserHolder;
import org.mycompany.report.service.ExcelService;
import org.mycompany.report.service.ReportService;
import org.mycompany.report.service.api.IExcelService;
import org.mycompany.report.web.clients.IAuditClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;


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
    public ExcelService excelService() {
        return new ExcelService();
    }
    @Bean
    public ReportService reportService(IExcelService excelService,
                                       IReportRepository reportRepository,
                                       IAuditClient auditClient,
                                       MinioClient minioClient,
                                       MinioProperty minioProperty,
                                       Converter<ReportDTO, Report> auditToEntity,
                                       Converter<Report, ReportInfoDTO> toDTO) {
        return new ReportService(excelService, reportRepository, auditClient,
                minioClient, minioProperty, auditToEntity, toDTO);
    }
}
