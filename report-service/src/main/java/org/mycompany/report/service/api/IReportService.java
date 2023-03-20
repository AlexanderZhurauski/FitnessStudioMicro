package org.mycompany.report.service.api;

import org.mycompany.report.core.dto.enums.ReportType;
import org.mycompany.report.core.dto.report.ReportDTO;
import org.mycompany.report.core.dto.report.ReportInfoDTO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IReportService {

    void create(ReportType type, ReportDTO reportDTO);
    Page<ReportInfoDTO> getPage(Pageable pageable);
    InputStreamResource export(UUID uuid);
    boolean isAvailable(UUID uuid);
}
