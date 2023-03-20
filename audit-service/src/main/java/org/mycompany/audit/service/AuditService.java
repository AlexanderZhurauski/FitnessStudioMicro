package org.mycompany.audit.service;

import org.mycompany.audit.core.dto.AuditDTO;
import org.mycompany.audit.core.dto.ReportDTO;
import org.mycompany.audit.core.exceptions.custom.EntityNotFoundException;
import org.mycompany.audit.dao.entities.AuditEntity;
import org.mycompany.audit.dao.repositories.IAuditRepository;
import org.mycompany.audit.service.api.IAuditService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public List<AuditDTO> getAuditData(ReportDTO reportDTO) {

        Instant from = instantFromDate(reportDTO.getFrom());
        Instant to = instantFromDate(reportDTO.getTo())
                .atZone(ZoneId.systemDefault())
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999)
                .toInstant();
        List<AuditDTO> auditData = getAuditData(reportDTO.getUser(), from, to)
                .stream()
                .map(auditEntity -> this.toDTOConverter.convert(auditEntity))
                .collect(Collectors.toList());

        return auditData;
    }

    private Instant instantFromDate(LocalDate date) {
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    private List<AuditEntity> getAuditData(UUID userUUID, Instant from, Instant to) {

        return this.auditRepository.findAllByUserUUIDAndCreationTimeBetweenOrderByCreationTime(userUUID, from, to);
    }
}
