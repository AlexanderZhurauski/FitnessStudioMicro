package org.mycompany.report.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.mycompany.report.audit.annotations.Audited;
import org.mycompany.report.audit.enums.EntityType;
import org.mycompany.report.audit.enums.OperationType;
import org.mycompany.report.config.MinioProperty;
import org.mycompany.report.core.dto.audit.AuditDTO;
import org.mycompany.report.core.dto.enums.ReportStatus;
import org.mycompany.report.core.dto.report.ReportDTO;
import org.mycompany.report.core.dto.report.ReportInfoDTO;
import org.mycompany.report.core.exceptions.custom.EntityNotFoundException;
import org.mycompany.report.core.exceptions.custom.ExcelExportException;
import org.mycompany.report.dao.entities.Report;
import org.mycompany.report.dao.repositories.IReportRepository;
import org.mycompany.report.service.api.IExcelService;
import org.mycompany.report.service.api.IReportService;
import org.mycompany.report.web.clients.IAuditClient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

public class ReportService implements IReportService {

    private static final String EXCEL_FILE = "%s.xlsx";
    private static final long UNKNOWN_SIZE = -1L;
    private static final long UNKNOWN_PARTS = 10485760L;

    private IExcelService excelService;
    private IReportRepository reportRepository;
    private IAuditClient auditClient;
    private MinioClient minioClient;
    private MinioProperty minioProperty;
    private JobScheduler jobScheduler;
    private Converter<ReportDTO, Report> auditToEntity;
    private Converter<Report, ReportInfoDTO> toDTO;

    public ReportService(IExcelService excelService,
                         IReportRepository reportRepository,
                         IAuditClient auditClient,
                         MinioClient minioClient,
                         MinioProperty minioProperty,
                         JobScheduler jobScheduler,
                         Converter<ReportDTO, Report> auditToEntity,
                         Converter<Report, ReportInfoDTO> toDTO) {
        this.excelService = excelService;
        this.reportRepository = reportRepository;
        this.auditClient = auditClient;
        this.minioClient = minioClient;
        this.minioProperty = minioProperty;
        this.jobScheduler = jobScheduler;
        this.auditToEntity = auditToEntity;
        this.toDTO = toDTO;
    }

    @Override
    @Transactional
    @Audited(operationType = OperationType.CREATE, entityType = EntityType.REPORT)
    public UUID createAuditReport(ReportDTO reportDTO) {
        Report reportInfo = this.auditToEntity.convert(reportDTO);
        UUID reportUUID = this.reportRepository.save(reportInfo).getUuid();

        List<AuditDTO> auditData = this.auditClient.getReportData(reportDTO);
        Report reportBeingProcessed = this.reportRepository.findById(reportUUID)
                .orElseThrow(() -> new EntityNotFoundException(reportUUID, "report"));
        reportBeingProcessed.setStatus(ReportStatus.PROGRESS);
        this.reportRepository.save(reportBeingProcessed);
        this.jobScheduler.enqueue(() -> auditExcelTask(auditData, reportUUID));

        return reportUUID;
    }

    @Override
    public Page<ReportInfoDTO> getPage(Pageable pageable) {
        return this.reportRepository.findAll(pageable)
                .map(this.toDTO::convert);
    }

    @Override
    public InputStreamResource export(UUID reportUUID) {
        GetObjectArgs downloadConfig = GetObjectArgs.builder()
                .bucket(minioProperty.getExcelBucket())
                .object(String.format(EXCEL_FILE, reportUUID.toString()))
                .build();

        try {
            return new InputStreamResource(this.minioClient.getObject(downloadConfig));
        } catch (Exception e) {
            throw new ExcelExportException("Failed to export report '"
                    + reportUUID + "'!", e);
        }
    }

    @Override
    public boolean isAvailable(UUID uuid) {
        return this.reportRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid, "report"))
                .getStatus().equals(ReportStatus.DONE);
    }

    @Job(name = "Audit Report Creation", retries = 1)
    public void auditExcelTask(List<AuditDTO> auditData, UUID reportUUID) {
        InputStreamResource excelDoc;
        Report reportBeingProcessed = this.reportRepository.findById(reportUUID)
                .orElseThrow(() -> new EntityNotFoundException(reportUUID, "report"));

        try {
            excelDoc = this.excelService.convertToExcel(auditData);
            saveExcelToCloud(excelDoc, reportUUID);
        } catch (Exception e) {
            reportBeingProcessed.setStatus(ReportStatus.ERROR);
            this.reportRepository.save(reportBeingProcessed);
            throw new RuntimeException(e);
        }

        reportBeingProcessed.setStatus(ReportStatus.DONE);
        this.reportRepository.save(reportBeingProcessed);
    }
    private void saveExcelToCloud(InputStreamResource excelDoc, UUID reportUUID) throws IOException,
            ServerException, InsufficientDataException,
            ErrorResponseException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException {

        PutObjectArgs uploadConfig = PutObjectArgs.builder()
                .bucket(this.minioProperty.getExcelBucket())
                .object(String.format(EXCEL_FILE, reportUUID.toString()))
                .stream(excelDoc.getInputStream(), UNKNOWN_SIZE, UNKNOWN_PARTS)
                .build();

        this.minioClient.putObject(uploadConfig);
    }
}
