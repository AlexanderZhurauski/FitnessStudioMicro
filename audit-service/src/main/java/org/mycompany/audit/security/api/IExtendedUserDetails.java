package org.mycompany.audit.security.api;

import org.mycompany.audit.core.dto.enums.UserRole;
import org.mycompany.audit.core.dto.enums.UserStatus;
import org.springframework.security.core.userdetails.UserDetails;

public interface IExtendedUserDetails extends UserDetails {

    String getFullName();
    String getUserID();
    UserRole getRole();
    UserStatus getStatus();
}
