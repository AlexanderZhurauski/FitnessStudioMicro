package org.mycompany.user.audit.annotations;

import org.mycompany.user.audit.enums.EntityType;
import org.mycompany.user.audit.enums.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Audited {
    OperationType operationType();
    EntityType entityType();
}
