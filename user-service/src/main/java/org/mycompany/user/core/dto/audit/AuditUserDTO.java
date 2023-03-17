package org.mycompany.user.core.dto.audit;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.mycompany.user.core.dto.enums.UserRole;

import java.util.UUID;

public class AuditUserDTO {
    @NotNull
    private UUID uuid;
    @NotNull
    @NotBlank
    @Email
    private String mail;
    @NotNull
    @NotBlank
    private String fio;
    @NotNull
    private UserRole role;

    public AuditUserDTO() {
    }

    public AuditUserDTO(UUID uuid, String mail,
                        String fio, UserRole role) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFio() {
        return this.fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
