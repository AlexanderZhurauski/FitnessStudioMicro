package org.mycompany.user.config;

import org.mycompany.user.converters.domain.*;
import org.mycompany.user.core.dto.user.UserCreateDTO;
import org.mycompany.user.core.dto.user.UserDTO;
import org.mycompany.user.core.dto.user.UserDetailsDTO;
import org.mycompany.user.core.dto.user.UserRegistrationDTO;
import org.mycompany.user.dao.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ConverterConfig {

    @Bean
    public Converter<User, UserDTO> userToDTOConverter() {
        return new UserToDTOConverter();
    }

    @Bean
    public Converter<UserCreateDTO, User> userToEntityConverter(PasswordEncoder passwordEncoder) {
        return new UserToEntityConverter(passwordEncoder);
    }
    @Bean
    public Converter<UserRegistrationDTO, UserCreateDTO> registrationConverter(PasswordEncoder passwordEncoder) {
        return new UserRegistrationConverter(passwordEncoder);
    }
    @Bean
    public Converter<UserDetails, UserDetailsDTO> userDetailsConverter() {
        return new UserDetailsToDTO();
    }
}
