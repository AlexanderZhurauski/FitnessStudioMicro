package org.mycompany.report.config;

import org.mycompany.report.converters.domain.AuditReportToEntityConverter;
import org.mycompany.report.converters.domain.ReportToDTOConverter;
import org.mycompany.report.core.dto.report.ReportDTO;
import org.mycompany.report.core.dto.report.ReportInfoDTO;
import org.mycompany.report.dao.entities.Report;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class ConverterConfig {

    @Bean
    public Converter<ReportDTO, Report> auditReportToEntityConverter() {
        return new AuditReportToEntityConverter();
    }
    @Bean
    public Converter<Report, ReportInfoDTO> reportToDTOConverter() {
        return new ReportToDTOConverter();
    }
}
