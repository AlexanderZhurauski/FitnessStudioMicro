package org.mycompany.user.service;

import org.mycompany.user.audit.annotations.Audited;
import org.mycompany.user.audit.enums.EntityType;
import org.mycompany.user.audit.enums.OperationType;
import org.mycompany.user.core.dto.enums.UserStatus;
import org.mycompany.user.core.dto.user.*;
import org.mycompany.user.security.JwtTokenHandler;
import org.mycompany.user.security.UserHolder;
import org.mycompany.user.security.api.IExtendedUserDetails;
import org.mycompany.user.service.api.IUserAuthenticationService;
import org.mycompany.user.service.api.IUserDataService;
import org.mycompany.user.web.clients.IMailClient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class UserAuthenticationService implements IUserAuthenticationService {

    private UserDetailsService userDetailsService;
    private IUserDataService userDataService;
    private IMailClient mailClient;
    private UserHolder userHolder;
    private Converter<UserRegistrationDTO, UserCreateDTO> registrationConverter;
    private JwtTokenHandler tokenUtil;
    private PasswordEncoder passwordEncoder;

    public UserAuthenticationService(UserDetailsService userDetailsService,
                                     IUserDataService userDataService,
                                     IMailClient mailClient,
                                     UserHolder userHolder,
                                     Converter<UserRegistrationDTO, UserCreateDTO> registrationConverter,
                                     JwtTokenHandler tokenUtil,
                                     PasswordEncoder passwordEncoder) {

        this.userDetailsService = userDetailsService;
        this.userDataService = userDataService;
        this.mailClient = mailClient;
        this.userHolder = userHolder;
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
    @Audited(entityType = EntityType.USER, operationType = OperationType.UPDATE)
    @Transactional
    public UUID verify(String code, String mail) {

        if (!this.mailClient.verifyEmail(code, mail)) {
            throw new BadCredentialsException("The token provided doesn't " +
                    "match the token assigned to email '" + mail + "'");
        }
        IExtendedUserDetails userDetails = (IExtendedUserDetails) this.userDetailsService.loadUserByUsername(mail);
        UUID userID = UUID.fromString(userDetails.getUserID());
        UserDTO confirmedUser = this.userDataService.get(userID);

        this.userDataService.changeStatus(userID, confirmedUser.getBaseEssence().getLastUpdated(),
                UserStatus.ACTIVATED);

        return userID;
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {

        String userEmail = userLoginDTO.getMail();
        IExtendedUserDetails loadedUser = (IExtendedUserDetails) this.userDetailsService.loadUserByUsername(userEmail);
        String enteredPassword = userLoginDTO.getPassword();
        String actualPassword = loadedUser.getPassword();

        if (!this.passwordEncoder.matches(enteredPassword, actualPassword)) {
            throw new BadCredentialsException("Invalid password provided!");
        }

        return this.tokenUtil.generateAccessToken(loadedUser);
    }
    @Override
    public UserDTO getMyData() {

        IExtendedUserDetails user = (IExtendedUserDetails) this.userHolder.getAuthentication().getPrincipal();
        UUID userID = UUID.fromString(user.getUserID());

        return this.userDataService.get(userID);
    }

    @Override
    public UserDetailsDTO getInternal(String username) {

        return (UserDetailsDTO) this.userDetailsService.loadUserByUsername(username);
    }
}
