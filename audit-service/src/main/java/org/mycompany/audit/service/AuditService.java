package org.mycompany.audit.service;

import org.mycompany.audit.core.dto.AuditDTO;
import org.mycompany.audit.dao.repositories.IAuditRepository;
import org.mycompany.audit.service.api.IAuditService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class AuditService implements IAuditService {

    private IAuditRepository auditRepository;
    @Override
    public AuditDTO get(UUID uuid) {
        return null;
    }

    @Override
    public Page<AuditDTO> getPage(Pageable pageable) {
        return null;
    }

    @Override
    public void post(AuditDTO auditDTO) {

    }
}
