package org.mycompany.audit.converters.domain;

import org.mycompany.audit.core.dto.AuditDTO;
import org.mycompany.audit.core.dto.AuditUserDTO;
import org.mycompany.audit.dao.entities.AuditEntity;
import org.mycompany.audit.dao.entities.AuditUserEntity;
import org.springframework.core.convert.converter.Converter;

public class AuditToDTOConverter implements Converter<AuditEntity, AuditDTO> {
    @Override
    public AuditDTO convert(AuditEntity entity) {

        AuditDTO dto = new AuditDTO();
        dto.setId(entity.getId());
        dto.setText(entity.getText());
        dto.setUuid(entity.getUuid());
        dto.setType(entity.getType().getType());
        dto.setCreationTime(entity.getCreationTime());

        AuditUserEntity userEntity = entity.getUser();
        AuditUserDTO userDTO = new AuditUserDTO();
        userDTO.setFio(userEntity.getFio());
        userDTO.setMail(userEntity.getMail());
        userDTO.setRole(userEntity.getRole().getRole());
        userDTO.setUuid(userEntity.getUuid());
        dto.setUser(userDTO);

        return dto;
    }
}
