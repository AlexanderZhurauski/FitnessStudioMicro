package org.mycompany.audit.converters.domain;

import org.mycompany.audit.core.dto.AuditDTO;
import org.mycompany.audit.core.dto.AuditUserDTO;
import org.mycompany.audit.dao.entities.AuditEntity;
import org.mycompany.audit.dao.entities.AuditUserEntity;
import org.mycompany.audit.dao.entities.Role;
import org.mycompany.audit.dao.entities.Type;
import org.springframework.core.convert.converter.Converter;

public class AuditToEntityConverter implements Converter<AuditDTO, AuditEntity> {
    @Override
    public AuditEntity convert(AuditDTO dto) {

        AuditEntity entity = new AuditEntity();
        entity.setId(dto.getId());
        entity.setCreationTime(dto.getCreationTime());
        entity.setUuid(dto.getUuid());
        entity.setType(new Type(dto.getType()));
        entity.setText(dto.getText());

        AuditUserEntity userEntity = new AuditUserEntity();
        AuditUserDTO userDTO = dto.getUser();
        userEntity.setUuid(userDTO.getUuid());
        userEntity.setMail(userDTO.getMail());
        userEntity.setRole(new Role(userDTO.getRole()));
        userEntity.setFio(userDTO.getFio());
        entity.setUser(userEntity);

        return entity;
    }
}
