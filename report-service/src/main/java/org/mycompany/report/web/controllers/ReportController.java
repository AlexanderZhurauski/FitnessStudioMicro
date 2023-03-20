package org.mycompany.report.web.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.mycompany.report.core.dto.enums.ReportType;
import org.mycompany.report.core.dto.report.ReportDTO;
import org.mycompany.report.core.dto.report.ReportInfoDTO;
import org.mycompany.report.service.api.IReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    private static final String FILE_ATTACHMENT = "attachment; filename=%s.xlsx";
    private static final String EXCEL_SPREADSHEET = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private final IReportService reportService;

    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("{type}")
    public ResponseEntity<String> postReport(@Valid @NotNull @RequestBody ReportDTO reportDTO,
                                             @PathVariable @NotNull ReportType type) {

        this.reportService.createAuditReport(reportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping
    public ResponseEntity<Page<ReportInfoDTO>> getReportPage(Pageable pageable) {

        Page<ReportInfoDTO> reportPage = this.reportService.getPage(pageable);
        return ResponseEntity.ok(reportPage);
    }

    @GetMapping(path = "/{uuid}/export")
    public ResponseEntity<Resource> exportReport(@PathVariable @NotNull UUID uuid) {
        InputStreamResource excelFile = this.reportService.export(uuid);
        String excelAttachment = String.format(FILE_ATTACHMENT, uuid.toString());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, excelAttachment)
                .contentType(MediaType.parseMediaType(EXCEL_SPREADSHEET))
                .body(excelFile);
    }
    @RequestMapping(method = RequestMethod.HEAD, path = "/{uuid}/export")
    public ResponseEntity<String> checkAvailability(@PathVariable @NotNull UUID uuid) {

        boolean isAvailable = this.reportService.isAvailable(uuid);
        if (!isAvailable) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
