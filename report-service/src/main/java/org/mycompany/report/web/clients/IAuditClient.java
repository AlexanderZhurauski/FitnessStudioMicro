package org.mycompany.report.web.clients;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.mycompany.report.core.dto.audit.AuditDTO;
import org.mycompany.report.core.dto.report.ReportDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "audit-service")
public interface IAuditClient {

    @PostMapping("api/v1/audit/internalPost")
    ResponseEntity<String> internalPost(@NotNull(message = "Cannot post an empty audit")
                                        @RequestBody AuditDTO auditDTO);

    @GetMapping("api/v1/audit/getAuditData")
    ResponseEntity<List<AuditDTO>> getReportData(@Valid @NotNull @RequestBody ReportDTO reportDTO);
}
