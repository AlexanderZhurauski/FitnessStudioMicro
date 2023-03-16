package org.mycompany.audit.web.controllers;

import org.mycompany.audit.core.dto.AuditDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.UUID;

@RestController(value = "/api/v1/audit")
public class AuditController {

    @GetMapping
    public ResponseEntity<AuditDTO> getPage(@RequestParam Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<AuditDTO> get(@PathVariable UUID uuid) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<String> internalPost(@RequestBody AuditDTO auditDTO) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
