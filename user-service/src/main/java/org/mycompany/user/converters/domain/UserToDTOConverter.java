package org.mycompany.user.converters.domain;

import org.mycompany.user.core.dto.BaseEssence;
import org.mycompany.user.core.dto.user.UserDTO;
import org.mycompany.user.dao.entities.User;
import org.springframework.core.convert.converter.Converter;

public class UserToDTOConverter implements Converter<User, UserDTO> {
    @Override
    public UserDTO convert(User userEntity) {

        UserDTO userDTO = new UserDTO();
        BaseEssence baseEssence = new BaseEssence(userEntity.getUuid(),
                userEntity.getCreationTime(), userEntity.getLastUpdated());
        userDTO.setBaseEssence(baseEssence);
        userDTO.setMail(userEntity.getMail());
        userDTO.setFullName(userEntity.getFullName());
        userDTO.setRole(userEntity.getRole().getRole());
        userDTO.setStatus(userEntity.getStatus().getStatus());

        return userDTO;
    }
}
