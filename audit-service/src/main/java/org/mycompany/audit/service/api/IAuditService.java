package org.mycompany.audit.service.api;

import org.mycompany.audit.core.dto.AuditDTO;
import org.mycompany.audit.core.dto.ReportDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IAuditService {

    AuditDTO get(UUID uuid);
    Page<AuditDTO> getPage(Pageable pageable);
    void post(AuditDTO auditDTO);
    List<AuditDTO> getAuditData(ReportDTO reportDTO);
}
