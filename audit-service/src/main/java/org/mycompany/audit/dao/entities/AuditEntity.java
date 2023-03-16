package org.mycompany.audit.dao.entities;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
@Entity
@Table(name = "audit_entity", schema = "app")
public class AuditEntity {

    @Id
    private UUID uuid;
    @Column(name = "creation_time")
    private Instant creationTime;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private AuditUserEntity user;
    private String text;
    @Enumerated(EnumType.STRING)
    @ManyToOne
    private Type type;
    @Column(name = "entity_id")
    private String id;

    public AuditEntity() {
    }

    public AuditEntity(UUID uuid, Instant creationTime,
                       AuditUserEntity user, String text,
                       Type type, String id) {
        this.uuid = uuid;
        this.creationTime = creationTime;
        this.user = user;
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

    public AuditUserEntity getUser() {
        return this.user;
    }

    public void setUser(AuditUserEntity user) {
        this.user = user;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
