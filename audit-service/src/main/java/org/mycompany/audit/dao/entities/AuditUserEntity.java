package org.mycompany.audit.dao.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "audit_user")
public class AuditUserEntity {

    @Id
    private UUID uuid;
    private String mail;
    @Column(name = "full_name")
    private String fio;
    @Enumerated(EnumType.STRING)
    @ManyToOne
    private Role role;

    public AuditUserEntity() {
    }

    public AuditUserEntity(UUID uuid, String mail,
                           String fio, Role role) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
    }

    public UUID getUuid() {
        return this.uuid;
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

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
