package org.mycompany.report.converters.domain;

import org.mycompany.report.core.dto.enums.ReportStatus;
import org.mycompany.report.core.dto.enums.ReportType;
import org.mycompany.report.core.dto.report.ReportDTO;
import org.mycompany.report.dao.entities.Report;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

public class AuditReportToEntityConverter implements Converter<ReportDTO, Report> {

    private static final String DESCRIPTION = "An audit report for %s-%s for user '%s'";
    @Override
    public Report convert(ReportDTO reportDTO) {

        Report report = new Report();
        LocalDate fromDate = reportDTO.getFrom();
        LocalDate toDate = reportDTO.getTo();
        UUID userID = report.getUserID();
        String description = String.format(DESCRIPTION, fromDate, toDate, userID);

        report.setFrom(instantFromDate(fromDate));
        Instant to = instantFromDate(toDate)
                .atZone(ZoneId.systemDefault())
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999)
                .toInstant();
        report.setTo(to);
        report.setUserID(userID);
        report.setDescription(description);
        report.setType(ReportType.JOURNAL_AUDIT);
        report.setStatus(ReportStatus.LOADED);
        report.setUuid(UUID.randomUUID());

        return report;
    }

    private Instant instantFromDate(LocalDate date) {
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }
}
