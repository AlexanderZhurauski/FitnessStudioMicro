package org.mycompany.user.service;

import jakarta.persistence.OptimisticLockException;
import org.mycompany.user.audit.annotations.Audited;
import org.mycompany.user.audit.enums.EntityType;
import org.mycompany.user.audit.enums.OperationType;
import org.mycompany.user.core.dto.enums.UserStatus;
import org.mycompany.user.core.dto.user.UserCreateDTO;
import org.mycompany.user.core.dto.user.UserDTO;
import org.mycompany.user.core.exceptions.custom.EntityNotFoundException;
import org.mycompany.user.dao.entities.Role;
import org.mycompany.user.dao.entities.Status;
import org.mycompany.user.dao.entities.User;
import org.mycompany.user.dao.repositories.IUserDataRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.mycompany.user.service.api.IUserDataService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.UUID;

public class UserDataService implements IUserDataService {

    private IUserDataRepository userRepository;
    private Converter<UserCreateDTO, User> toEntityConverter;
    private Converter<User, UserDTO> toDTOConverter;
    private PasswordEncoder passwordEncoder;

    public UserDataService(IUserDataRepository userRepository,
                           Converter<UserCreateDTO, User> toEntityConverter,
                           Converter<User, UserDTO> toDTOConverter,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.toEntityConverter = toEntityConverter;
        this.toDTOConverter = toDTOConverter;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Audited(operationType = OperationType.CREATE, entityType = EntityType.USER)
    public UUID create(UserCreateDTO userCreateDTO) {

        User user = this.toEntityConverter.convert(userCreateDTO);
        return this.userRepository.save(user).getUuid();
    }

    @Override
    public Page<UserDTO> getPage(Pageable pageable) {
        Page<User> userPage = this.userRepository.findAll(pageable);
        Page<UserDTO> userPageDTO = userPage.map(this.toDTOConverter::convert);
        return userPageDTO;
    }

    @Override
    public UserDTO get(UUID uuid) {
        User user = this.userRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid, "user"));
        return toDTOConverter.convert(user);
    }

    @Override
    @Audited(operationType = OperationType.UPDATE, entityType = EntityType.USER)
    public UUID update(UUID uuid, Instant lastUpdated, UserCreateDTO userCreateDTO) {
        User user = this.userRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid, "user"));

        if (user.getLastUpdated().toEpochMilli() != lastUpdated.toEpochMilli()) {
            throw new OptimisticLockException("User with id '" + user.getUuid()
                    + "' has already been modified!");
        }

        user.setMail(userCreateDTO.getMail());
        user.setRole(new Role(userCreateDTO.getRole()));
        user.setPassword(this.passwordEncoder.encode(userCreateDTO.getPassword()));
        user.setFullName(userCreateDTO.getFullName());
        user.setStatus(new Status(userCreateDTO.getStatus()));
        return this.userRepository.save(user).getUuid();
    }
    @Override
    public boolean isActivated(String mail) {

        User queriedUser = this.userRepository.findByMail(mail)
                .orElseThrow(() -> new EntityNotFoundException(mail, "user"));
        return UserStatus.ACTIVATED.equals(queriedUser.getStatus().getStatus());
    }

    @Override
    @Audited(operationType = OperationType.UPDATE, entityType = EntityType.USER)
    public UUID changeStatus(UUID uuid, Instant lastUpdated, UserStatus status) {
        User user = this.userRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid, "user"));

        if (user.getLastUpdated().toEpochMilli() != lastUpdated.toEpochMilli()) {
            throw new OptimisticLockException("User with id '" + user.getUuid()
                    + "' has already been modified!");
        }

        user.setStatus(new Status(status));
        return this.userRepository.save(user).getUuid();
    }
}
