package org.mycompany.user.service.api;

import org.mycompany.user.core.dto.enums.UserStatus;
import org.mycompany.user.core.dto.user.UserCreateDTO;
import org.mycompany.user.core.dto.user.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

public interface IUserDataService {

    void create(UserCreateDTO userCreateDTO);
    Page<UserDTO> getPage(Pageable pageable);
    UserDTO get(UUID uuid);
    void update(UUID uuid, Instant lastUpdated, UserCreateDTO userCreateDTO);
    boolean isActivated(String mail);
    void changeStatus(UUID uuid, Instant lastUpdated, UserStatus status);
}
