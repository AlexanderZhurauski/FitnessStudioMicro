package org.mycompany.audit.dao.entities;

import jakarta.persistence.*;
import org.mycompany.audit.core.dto.enums.EntityType;
import org.mycompany.audit.core.dto.enums.UserRole;

import java.time.Instant;
import java.util.UUID;
@Entity
@Table(name = "audit_entity", schema = "app")
public class AuditEntity {

    @Id
    private UUID uuid;
    @Column(name = "creation_time")
    private Instant creationTime;
    @Column(name = "user_id")
    private UUID userUUID;
    private String mail;
    @Column(name = "full_name")
    private String fio;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String text;
    @Enumerated(EnumType.STRING)
    private EntityType type;
    @Column(name = "entity_id")
    private String id;

    public AuditEntity() {
    }

    public AuditEntity(UUID uuid, Instant creationTime, UUID userUUID,
                       String mail, String fio, UserRole role,
                       String text, EntityType type, String id) {
        this.uuid = uuid;
        this.creationTime = creationTime;
        this.userUUID = userUUID;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.text = text;
        this.type = type;
        this.id = id;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Instant getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    public UUID getUserUUID() {
        return this.userUUID;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
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

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EntityType getType() {
        return this.type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
