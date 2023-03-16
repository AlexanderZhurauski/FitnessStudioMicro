package org.mycompany.audit.config;

import org.mycompany.audit.converters.domain.AuditToDTOConverter;
import org.mycompany.audit.converters.domain.AuditToEntityConverter;
import org.mycompany.audit.core.dto.AuditDTO;
import org.mycompany.audit.dao.entities.AuditEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class ConverterConfig {

    @Bean
    public Converter<AuditEntity, AuditDTO> toDTOConverter() {
        return new AuditToDTOConverter();
    }

    @Bean
    public Converter<AuditDTO, AuditEntity> toEnityConverter() {
        return new AuditToEntityConverter();
    }
}
