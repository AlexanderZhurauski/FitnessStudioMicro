package org.mycompany.product.audit.annotations;

import org.mycompany.product.audit.enums.EntityType;
import org.mycompany.product.audit.enums.OperationType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Audited {
    OperationType operationType();
    EntityType entityType();
}
