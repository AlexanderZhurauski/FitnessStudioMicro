package org.mycompany.user.security.api;

import org.mycompany.user.core.dto.enums.UserRole;
import org.mycompany.user.core.dto.enums.UserStatus;
import org.springframework.security.core.userdetails.UserDetails;

public interface IExtendedUserDetails extends UserDetails {

    String getFullName();
    String getUserID();
    UserRole getRole();
    UserStatus getStatus();
}
