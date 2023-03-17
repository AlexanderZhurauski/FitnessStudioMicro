package org.mycompany.user.web.clients;

import jakarta.validation.constraints.NotNull;
import org.mycompany.user.core.dto.audit.AuditDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "audit-service")
public interface IAuditClient {

    @PostMapping("api/v1/audit/internalPost")
    ResponseEntity<String> internalPost(@NotNull(message = "Cannot post an empty audit")
                                        @RequestBody AuditDTO auditDTO);
}
