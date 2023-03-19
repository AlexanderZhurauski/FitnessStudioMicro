package org.mycompany.report.audit.annotations;

import org.mycompany.report.audit.enums.EntityType;
import org.mycompany.report.audit.enums.OperationType;

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
