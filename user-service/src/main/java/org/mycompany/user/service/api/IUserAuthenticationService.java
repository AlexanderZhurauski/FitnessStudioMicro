package org.mycompany.user.service.api;

import org.mycompany.user.core.dto.user.UserDTO;
import org.mycompany.user.core.dto.user.UserDetailsDTO;
import org.mycompany.user.core.dto.user.UserLoginDTO;
import org.mycompany.user.core.dto.user.UserRegistrationDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface IUserAuthenticationService {

    void register(UserRegistrationDTO userRegistrationDTO);
    void verify(String code, String mail) throws ExecutionException, InterruptedException, TimeoutException;
    String login(UserLoginDTO userLoginDTO);
    UserDTO getMyData();
    UserDetailsDTO getInternal(String username);
}
