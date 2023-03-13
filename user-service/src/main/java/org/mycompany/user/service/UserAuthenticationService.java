package org.mycompany.user.service;

import org.mycompany.user.core.dto.enums.UserStatus;
import org.mycompany.user.core.dto.user.UserCreateDTO;
import org.mycompany.user.core.dto.user.UserDTO;
import org.mycompany.user.core.dto.user.UserLoginDTO;
import org.mycompany.user.core.dto.user.UserRegistrationDTO;
import org.mycompany.user.dao.entities.User;
import org.mycompany.user.security.JwtTokenUtil;
import org.mycompany.user.security.UserHolder;
import org.mycompany.user.service.api.IUserAuthenticationService;
import org.mycompany.user.service.api.IUserDataService;
import org.mycompany.user.web.clients.IMailClient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UserAuthenticationService implements IUserAuthenticationService {

    private UserDetailsService userDetailsService;
    private IUserDataService userDataService;
    private IMailClient mailClient;
    private UserHolder userHolder;
    private Converter<User, UserDTO> toDTOConverter;
    private Converter<UserRegistrationDTO, UserCreateDTO> registrationConverter;
    private JwtTokenUtil tokenUtil;
    private PasswordEncoder passwordEncoder;

    public UserAuthenticationService(UserDetailsService userDetailsService,
                                     IUserDataService userDataService,
                                     IMailClient mailClient,
                                     UserHolder userHolder,
                                     Converter<User, UserDTO> toDTOConverter,
                                     Converter<UserRegistrationDTO, UserCreateDTO> registrationConverter,
                                     JwtTokenUtil tokenUtil,
                                     PasswordEncoder passwordEncoder) {

        this.userDetailsService = userDetailsService;
        this.userDataService = userDataService;
        this.mailClient = mailClient;
        this.userHolder = userHolder;
        this.toDTOConverter = toDTOConverter;
        this.registrationConverter = registrationConverter;
        this.tokenUtil = tokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(UserRegistrationDTO userRegistrationDTO) {

        UserCreateDTO createDTO = this.registrationConverter.convert(userRegistrationDTO);
        this.userDataService.create(createDTO);
        this.mailClient.sendConfirmationEmail(createDTO.getMail());
    }

    @Override
    public void verify(String code, String mail)
            throws ExecutionException, InterruptedException, TimeoutException {

        if (!this.mailClient.verifyEmail(mail, code).get(15, TimeUnit.SECONDS)) {
            throw new BadCredentialsException("The token provided doesn't " +
                    "match the token assigned to email '" + mail + "'");
        }
        User confirmedUser = (User) this.userDetailsService.loadUserByUsername(mail);
        this.userDataService.changeStatus(confirmedUser.getUuid(),
                confirmedUser.getLastUpdated(), UserStatus.ACTIVATED);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {

        String userEmail = userLoginDTO.getMail();
        UserDetails loadedUser = this.userDetailsService.loadUserByUsername(userEmail);
        String enteredPassword = userLoginDTO.getPassword();
        String actualPassword = loadedUser.getPassword();

        if (!this.passwordEncoder.matches(enteredPassword, actualPassword)) {
            throw new BadCredentialsException("Invalid password provided!");
        }

        return this.tokenUtil.generateAccessToken(loadedUser);
    }
    @Override
    public UserDTO getMyData() {

        User user = (User) this.userHolder.getAuthentication().getPrincipal();
        return this.toDTOConverter.convert(user);
    }
}
