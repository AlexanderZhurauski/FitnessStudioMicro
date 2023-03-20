package org.mycompany.report.service;

import io.minio.MinioClient;
import org.mycompany.report.core.dto.enums.ReportType;
import org.mycompany.report.core.dto.report.ReportDTO;
import org.mycompany.report.core.dto.report.ReportInfoDTO;
import org.mycompany.report.dao.repositories.IReportRepository;
import org.mycompany.report.service.api.IReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class ReportService implements IReportService {

    private IReportRepository reportRepository;
    private MinioClient minioClient;
    public ReportService(IReportRepository reportRepository, MinioClient minioClient) {
        this.reportRepository = reportRepository;
        this.minioClient = minioClient;
    }

    @Override
    public void create(ReportType type, ReportDTO reportDTO) {

    }

    @Override
    public Page<ReportInfoDTO> getPage(Pageable pageable) {
        return null;
    }

    @Override
    public InputStreamResource export(UUID uuid) {
        return null;
    }

    @Override
    public boolean isAvailable(UUID uuid) {
        return false;
    }
}
