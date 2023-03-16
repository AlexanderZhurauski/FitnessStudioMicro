package org.mycompany.user.config;

import org.mycompany.user.core.dto.user.UserCreateDTO;
import org.mycompany.user.core.dto.user.UserDTO;
import org.mycompany.user.core.dto.user.UserDetailsDTO;
import org.mycompany.user.core.dto.user.UserRegistrationDTO;
import org.mycompany.user.dao.entities.*;
import org.mycompany.user.dao.repositories.*;
import org.mycompany.user.security.JwtTokenHandler;
import org.mycompany.user.security.UserHolder;
import org.mycompany.user.service.*;
import org.mycompany.user.service.api.*;
import org.mycompany.user.web.clients.IMailClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class ServiceConfig {

    @Bean
    public IUserDataService userDataService(IUserDataRepository userRepository,
                                            Converter<UserCreateDTO, User> toEntityConverter,
                                            Converter<User, UserDTO> toDTOConverter,
                                            PasswordEncoder passwordEncoder) {

        return new UserDataService(userRepository, toEntityConverter, toDTOConverter, passwordEncoder);
    }

    @Bean
    public IUserAuthenticationService userAuthenticationService(UserDetailsService userDetailsService,
                                                                IUserDataService userDataService,
                                                                IMailClient mailClient,
                                                                UserHolder userHolder,
                                                                Converter<User, UserDTO> toDTOConverter,
                                                                Converter<UserRegistrationDTO, UserCreateDTO> registrationConverter,
                                                                Converter<UserDetails, UserDetailsDTO> userDetailsConverter,
                                                                JwtTokenHandler tokenUtil,
                                                                PasswordEncoder passwordEncoder) {

        return new UserAuthenticationService(userDetailsService, userDataService, mailClient,
                userHolder, toDTOConverter, registrationConverter, userDetailsConverter,
                tokenUtil, passwordEncoder);
    }
    @Bean
    public UserDetailsService userDetailsService(IUserAuthenticationRepository authenticationRepository) {
        return username -> authenticationRepository.findUserByMail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email '"
                        + username + "' has not been found!"));
    }
}
