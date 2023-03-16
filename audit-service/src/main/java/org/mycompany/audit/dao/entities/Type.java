package org.mycompany.audit.dao.entities;

import jakarta.persistence.*;
import org.mycompany.audit.core.dto.enums.EntityType;

@Entity
@Table(schema = "app", name = "entity_type")
public class Type {

    @Id
    private int id;
    @Enumerated(EnumType.STRING)
    private EntityType type;

    public Type() {
    }

    public Type(EntityType type) {
        this.id = type.ordinal() + 1;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EntityType getType() {
        return this.type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }
}
