package org.mycompany.audit.config;


import org.mycompany.audit.core.dto.AuditDTO;
import org.mycompany.audit.dao.entities.AuditEntity;
import org.mycompany.audit.dao.repositories.IAuditRepository;
import org.mycompany.audit.service.AuditService;
import org.mycompany.audit.service.api.IAuditService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;


@Configuration
public class ServiceConfig {

    @Bean
    public IAuditService auditService(IAuditRepository auditRepository,
                                      Converter<AuditEntity, AuditDTO> toDTOConverter,
                                      Converter<AuditDTO, AuditEntity> toEntityConverter) {

        return new AuditService(auditRepository, toDTOConverter, toEntityConverter);
    }
}
