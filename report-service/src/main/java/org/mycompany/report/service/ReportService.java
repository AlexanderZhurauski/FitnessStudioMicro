package org.mycompany.report.service;

import io.minio.MinioClient;
import org.mycompany.report.core.dto.enums.ReportStatus;
import org.mycompany.report.core.dto.enums.ReportType;
import org.mycompany.report.core.dto.report.ReportDTO;
import org.mycompany.report.core.dto.report.ReportInfoDTO;
import org.mycompany.report.core.exceptions.custom.EntityNotFoundException;
import org.mycompany.report.dao.entities.Report;
import org.mycompany.report.dao.repositories.IReportRepository;
import org.mycompany.report.service.api.IReportService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class ReportService implements IReportService {

    private IReportRepository reportRepository;
    private MinioClient minioClient;
    private Converter<ReportDTO, Report> auditToEntity;
    private Converter<Report, ReportInfoDTO> toDTO;
    public ReportService(IReportRepository reportRepository, MinioClient minioClient,
                         Converter<ReportDTO, Report> auditToEntity,
                         Converter<Report, ReportInfoDTO> toDTO) {
        this.reportRepository = reportRepository;
        this.minioClient = minioClient;
        this.auditToEntity = auditToEntity;
        this.toDTO = toDTO;
    }

    @Override
    public UUID create(ReportType type, ReportDTO reportDTO) {
        switch(type) {
            case JOURNAL_AUDIT -> {
                return createAuditReport(reportDTO);
            }
            default -> throw new IllegalArgumentException("Invalid report type provided");
        }
    }

    @Override
    public Page<ReportInfoDTO> getPage(Pageable pageable) {
        return this.reportRepository.findAll(pageable)
                .map(this.toDTO::convert);
    }

    @Override
    public InputStreamResource export(UUID uuid) {
        return null;
    }

    @Override
    public boolean isAvailable(UUID uuid) {
        return this.reportRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid, "report"))
                .getStatus().equals(ReportStatus.DONE);
    }

    private UUID createAuditReport(ReportDTO reportDTO) {
        Report reportInfo = this.auditToEntity.convert(reportDTO);
        UUID reportUUID = this.reportRepository.save(reportInfo).getUuid();

        return reportUUID;
    }
}
