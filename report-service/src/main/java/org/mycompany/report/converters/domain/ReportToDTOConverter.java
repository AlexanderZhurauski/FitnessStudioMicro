package org.mycompany.report.converters.domain;

import org.mycompany.report.core.dto.report.ReportDTO;
import org.mycompany.report.core.dto.report.ReportInfoDTO;
import org.mycompany.report.dao.entities.Report;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class ReportToDTOConverter implements Converter<Report, ReportInfoDTO> {
    @Override
    public ReportInfoDTO convert(Report report) {

        ReportInfoDTO reportDTO = new ReportInfoDTO();
        ReportDTO reportParams = new ReportDTO();
        LocalDate from = instantToDate(report.getFrom());
        LocalDate to = instantToDate(report.getTo());

        reportParams.setFrom(from);
        reportParams.setTo(to);
        reportParams.setUser(report.getUserID());

        reportDTO.setParams(reportParams);
        reportDTO.setDescription(report.getDescription());
        reportDTO.setType(report.getType());
        reportDTO.setStatus(report.getStatus());
        reportDTO.setUuid(report.getUuid());
        reportDTO.setCreationTime(report.getCreationTime());
        reportDTO.setLastUpdated(report.getLastUpdated());

        return reportDTO;
    }

    private LocalDate instantToDate(Instant instant) {
        return  instant.atZone(ZoneId.systemDefault()).toLocalDateTime().toLocalDate();
    }
}
