package org.mycompany.product.security.api;

import java.util.Date;

public interface ITokenHandler {

    String generateAccessToken(IExtendedUserDetails user);
    String getUsername(String token);
    String getFullName(String token);
    String getUserID(String token);
    public Date getExpirationDate(String token);
    boolean validate(String token);
}
