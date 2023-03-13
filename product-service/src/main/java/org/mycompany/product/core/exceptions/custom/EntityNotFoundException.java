package org.mycompany.product.core.exceptions.custom;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {

    private String id;
    private String entityName;
    public EntityNotFoundException(UUID id, String entityName) {
        super("No " + entityName + " with id '" + id + "' has been found!");
        this.id = id.toString();
        this.entityName = entityName;
    }

    public EntityNotFoundException(String id, String entityName) {
        super("No " + entityName + " with id '" + id + "' has been found!");
        this.id = id;
        this.entityName = entityName;
    }

    public String getId() {
        return this.id;
    }

    public String getEntityName() {
        return this.entityName;
    }
}
