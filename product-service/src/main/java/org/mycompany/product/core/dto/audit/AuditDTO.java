package org.mycompany.product.core.dto.audit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.mycompany.product.converters.json.InstantToUnix;
import org.mycompany.product.converters.json.UnixToInstant;
import org.mycompany.product.audit.enums.EntityType;

import java.time.Instant;
import java.util.UUID;

@JsonPropertyOrder({"uuid", "dt_create"})
public class AuditDTO {
    @NotNull
    private UUID uuid;
    @JsonProperty("dt_create")
    @JsonSerialize(converter = InstantToUnix.class)
    @JsonDeserialize(converter = UnixToInstant.class)
    private Instant creationTime;
    @NotNull
    private AuditUserDTO user;
    @NotNull
    @NotBlank
    private String text;
    @NotNull
    private EntityType type;
    @NotNull
    @NotBlank
    private String id;

    public AuditDTO() {
    }

    public AuditDTO(UUID uuid, Instant creationTime,
                    AuditUserDTO user, String text,
                    EntityType type, String id) {
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

    public AuditUserDTO getUser() {
        return this.user;
    }

    public void setUser(AuditUserDTO user) {
        this.user = user;
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