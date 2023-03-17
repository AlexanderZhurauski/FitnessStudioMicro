package org.mycompany.audit.security.api;

import org.mycompany.audit.core.dto.UserDetailsDTO;

import java.util.Date;

public interface ITokenHandler {

    String generateAccessToken(IExtendedUserDetails user);
    String getUsername(String token);
    String getFullName(String token);
    String getUserID(String token);
    public Date getExpirationDate(String token);
    boolean validate(String token);
}
