package org.mycompany.product.audit.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.mycompany.product.audit.annotations.Audited;
import org.mycompany.product.audit.enums.EntityType;
import org.mycompany.product.audit.enums.OperationType;
import org.mycompany.product.security.UserHolder;
import org.mycompany.product.service.api.IProductService;
import org.mycompany.product.service.api.IRecipeService;
import org.mycompany.product.web.clients.IAuditClient;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

@Aspect
public class AuditAspect {

    private static final String message = "User '%s' has %s entity '%s'.";

    private IAuditClient auditClient;
    private UserHolder userHolder;

    public AuditAspect(IAuditClient auditClient,
                       UserHolder userHolder) {
        this.auditClient = auditClient;
        this.userHolder = userHolder;
    }

    @Pointcut("@annotation(audited)")
    public void isAudited(Audited audited) {
    }

    @AfterReturning(value = "isAudited(audited))",
            argNames = "joinPoint,audited,result", returning = "result")
    public void sendAuditData(JoinPoint joinPoint, Audited audited, Object result) {

        OperationType operationType = audited.operationType();
        String operationName;
        switch (operationType) {
            case CREATE -> operationName = "created the";
            case UPDATE -> operationName = "made an update to the";
            case DELETE -> operationName = "deleted the";
            default -> throw new IllegalArgumentException("Invalid operation type");
        }

        EntityType entityType = audited.entityType();
    }
}
