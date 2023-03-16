package org.mycompany.audit.service;

import org.mycompany.audit.core.dto.AuditDTO;
import org.mycompany.audit.core.exceptions.custom.EntityNotFoundException;
import org.mycompany.audit.dao.entities.AuditEntity;
import org.mycompany.audit.dao.repositories.IAuditRepository;
import org.mycompany.audit.service.api.IAuditService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class AuditService implements IAuditService {

    private IAuditRepository auditRepository;
    private Converter<AuditEntity, AuditDTO> toDTOConverter;
    private Converter<AuditDTO, AuditEntity> toEntityConverter;

    public AuditService(IAuditRepository auditRepository,
                        Converter<AuditEntity, AuditDTO> toDTOConverter,
                        Converter<AuditDTO, AuditEntity> toEntityConverter) {

        this.auditRepository = auditRepository;
        this.toDTOConverter = toDTOConverter;
        this.toEntityConverter = toEntityConverter;
    }

    @Override
    public AuditDTO get(UUID uuid) {

        AuditEntity auditEntity = this.auditRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid, "audit entity"));
        return this.toDTOConverter.convert(auditEntity);
    }

    @Override
    public Page<AuditDTO> getPage(Pageable pageable) {

        Page<AuditEntity> auditEntityPage = this.auditRepository.findAll(pageable);
        return auditEntityPage.map(toDTOConverter::convert);
    }

    @Override
    public void post(AuditDTO auditDTO) {

        AuditEntity auditEntity = this.toEntityConverter.convert(auditDTO);
        this.auditRepository.save(auditEntity);
    }
}
