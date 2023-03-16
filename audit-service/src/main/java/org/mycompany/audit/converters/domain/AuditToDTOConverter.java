package org.mycompany.audit.converters.domain;

import org.mycompany.audit.core.dto.AuditDTO;
import org.mycompany.audit.core.dto.AuditUserDTO;
import org.mycompany.audit.dao.entities.AuditEntity;
import org.springframework.core.convert.converter.Converter;

public class AuditToDTOConverter implements Converter<AuditEntity, AuditDTO> {
    @Override
    public AuditDTO convert(AuditEntity entity) {

        AuditDTO dto = new AuditDTO();
        dto.setId(entity.getId());
        dto.setText(entity.getText());
        dto.setUuid(entity.getUuid());
        dto.setType(entity.getType());
        dto.setCreationTime(entity.getCreationTime());

        AuditUserDTO userDTO = new AuditUserDTO();
        userDTO.setFio(entity.getFio());
        userDTO.setMail(entity.getMail());
        userDTO.setRole(entity.getRole());
        userDTO.setUuid(entity.getUuid());
        dto.setUser(userDTO);

        return dto;
    }
}
