package org.mycompany.report.converters.domain;

import org.mycompany.report.core.dto.enums.ReportStatus;
import org.mycompany.report.core.dto.enums.ReportType;
import org.mycompany.report.core.dto.report.ReportDTO;
import org.mycompany.report.dao.entities.Report;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.util.UUID;

public class AuditReportToEntityConverter implements Converter<ReportDTO, Report> {

    private static final String DESCRIPTION = "An audit report from %s to %s for user '%s'";
    @Override
    public Report convert(ReportDTO reportDTO) {

        Report report = new Report();
        LocalDate fromDate = reportDTO.getFrom();
        LocalDate toDate = reportDTO.getTo();
        UUID userID = reportDTO.getUser();
        String description = String.format(DESCRIPTION, fromDate, toDate, userID);

        report.setFrom(fromDate);
        report.setTo(toDate);
        report.setUserID(userID);
        report.setDescription(description);
        report.setType(ReportType.JOURNAL_AUDIT);
        report.setStatus(ReportStatus.LOADED);
        report.setUuid(UUID.randomUUID());

        return report;
    }
}
