package org.mycompany.audit.web.controllers;

import jakarta.validation.constraints.NotNull;
import org.mycompany.audit.core.dto.AuditDTO;
import org.mycompany.audit.service.api.IAuditService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {

    private IAuditService auditService;

    public AuditController(IAuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public ResponseEntity<Page<AuditDTO>> getPage(Pageable pageable) {

        return ResponseEntity.ok(this.auditService.getPage(pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<AuditDTO> get(@NotNull(message = "No id has been provided")
                                            @PathVariable UUID uuid) {

        return ResponseEntity.ok(this.auditService.get(uuid));
    }

    @PostMapping("/internalPost")
    public ResponseEntity<String> internalPost(@NotNull(message = "Cannot post an empty audit")
                                                   @RequestBody AuditDTO auditDTO) {

        this.auditService.post(auditDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
