package org.mycompany.product.audit.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.mycompany.product.audit.annotations.Audited;
import org.mycompany.product.audit.enums.OperationType;
import org.mycompany.product.core.dto.UserDetailsDTO;
import org.mycompany.product.security.UserHolder;
import org.mycompany.product.service.api.IProductService;
import org.mycompany.product.service.api.IRecipeService;
import org.mycompany.product.web.clients.IAuditClient;
import org.springframework.security.core.userdetails.UserDetails;

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
            argNames = "joinPoint, audited")
    public void sendAuditData(JoinPoint joinPoint, Audited audited) {

        OperationType type = audited.type();
        String operationName;
        switch (type) {
            case CREATE -> operationName = "created the";
            case UPDATE -> operationName = "made an update to the";
            case DELETE -> operationName = "deleted the";
            default -> throw new IllegalArgumentException("Invalid operation type");
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class clazz = signature.getDeclaringType();
        String entityType;
        if (IProductService.class.isAssignableFrom(clazz)) {
            entityType = "Product";
        } else if (IRecipeService.class.isAssignableFrom(clazz)) {
            entityType = "Recipe";
        } else {
            throw new IllegalArgumentException("Invalid entity type");
        }

        UserDetails userDetails = (UserDetails) this.userHolder.getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        String auditText = String.format(message, username, operationName, entityType);
        String getRole = userDetails.getAuthorities().stream().findFirst().toString();
        //getOtherUseData, clean this stuff up lol
    }
}
