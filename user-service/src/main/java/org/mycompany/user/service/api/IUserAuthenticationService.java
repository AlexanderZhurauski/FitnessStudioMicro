package org.mycompany.user.service.api;

import org.mycompany.user.core.dto.user.UserDTO;
import org.mycompany.user.core.dto.user.UserLoginDTO;
import org.mycompany.user.core.dto.user.UserRegistrationDTO;

public interface IUserAuthenticationService {

    void register(UserRegistrationDTO userRegistrationDTO);
    void verify(String code, String mail);
    String login(UserLoginDTO userLoginDTO);
    UserDTO getMyData();
}
