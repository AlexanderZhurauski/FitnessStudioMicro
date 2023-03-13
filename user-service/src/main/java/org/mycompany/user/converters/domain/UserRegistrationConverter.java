package org.mycompany.user.converters.domain;

import org.mycompany.user.core.dto.enums.UserRole;
import org.mycompany.user.core.dto.enums.UserStatus;
import org.mycompany.user.core.dto.user.UserCreateDTO;
import org.mycompany.user.core.dto.user.UserRegistrationDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserRegistrationConverter implements Converter<UserRegistrationDTO, UserCreateDTO> {

    private PasswordEncoder passwordEncoder;

    public UserRegistrationConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserCreateDTO convert(UserRegistrationDTO source) {
        UserCreateDTO createDTO = new UserCreateDTO();
        createDTO.setMail(source.getMail());
        createDTO.setFullName(source.getFullName());
        createDTO.setPassword(source.getPassword());
        createDTO.setRole(UserRole.USER);
        createDTO.setStatus(UserStatus.WAITING_ACTIVATION);

        return createDTO;
    }
}
