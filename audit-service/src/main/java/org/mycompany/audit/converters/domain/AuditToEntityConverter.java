package org.mycompany.audit.converters.domain;

import org.mycompany.audit.core.dto.AuditDTO;
import org.mycompany.audit.core.dto.AuditUserDTO;
import org.mycompany.audit.dao.entities.AuditEntity;
import org.springframework.core.convert.converter.Converter;

public class AuditToEntityConverter implements Converter<AuditDTO, AuditEntity> {
    @Override
    public AuditEntity convert(AuditDTO dto) {

        AuditEntity entity = new AuditEntity();
        entity.setId(dto.getId());
        entity.setCreationTime(dto.getCreationTime());
        entity.setUuid(dto.getUuid());
        entity.setType(dto.getType());
        entity.setText(dto.getText());

        AuditUserDTO userDTO = dto.getUser();
        entity.setUserUUID(userDTO.getUuid());
        entity.setMail(userDTO.getMail());
        entity.setRole(userDTO.getRole());
        entity.setFio(userDTO.getFio());

        return entity;
    }
}
