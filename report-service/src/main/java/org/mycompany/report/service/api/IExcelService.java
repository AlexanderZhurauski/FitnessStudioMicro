package org.mycompany.report.service.api;

import org.mycompany.report.core.dto.audit.AuditDTO;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.util.List;

public interface IExcelService {

    InputStreamResource convertToExcel(List<AuditDTO> auditData) throws IOException;
}
