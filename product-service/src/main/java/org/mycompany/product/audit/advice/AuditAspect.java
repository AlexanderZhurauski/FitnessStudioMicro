package org.mycompany.product.audit.advice;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.mycompany.product.audit.annotations.Audited;
import org.mycompany.product.audit.enums.EntityType;
import org.mycompany.product.audit.enums.OperationType;
import org.mycompany.product.core.dto.audit.AuditDTO;
import org.mycompany.product.core.dto.audit.AuditUserDTO;
import org.mycompany.product.security.UserHolder;
import org.mycompany.product.security.api.IExtendedUserDetails;
import org.mycompany.product.web.clients.IAuditClient;

import java.time.Instant;
import java.util.UUID;

@Aspect
public class AuditAspect {

    private static final String MESSAGE = "User '%s' has %s entity '%s'.";

    private final IAuditClient auditClient;
    private final UserHolder userHolder;

    public AuditAspect(IAuditClient auditClient,
                       UserHolder userHolder) {
        this.auditClient = auditClient;
        this.userHolder = userHolder;
    }

    @Pointcut("@annotation(audited)")
    public void isAudited(Audited audited) {
    }

    @AfterReturning(value = "isAudited(audited))",
            argNames = "audited, uuid", returning = "uuid")
    public void sendAuditData(Audited audited, UUID uuid) {

        AuditDTO auditData = new AuditDTO();
        OperationType operationType = audited.operationType();
        String operationName;
        switch (operationType) {
            case CREATE -> operationName = "created the";
            case UPDATE -> operationName = "made an update to the";
            case DELETE -> operationName = "deleted the";
            default -> throw new IllegalArgumentException("Invalid operation type");
        }

        EntityType entityType = audited.entityType();
        auditData.setUuid(UUID.randomUUID());
        auditData.setType(entityType);
        auditData.setCreationTime(Instant.now());
        auditData.setId(uuid.toString());

        AuditUserDTO auditUser = new AuditUserDTO();
        IExtendedUserDetails userDetails = (IExtendedUserDetails) this.userHolder.getAuthentication().getPrincipal();
        auditUser.setMail(userDetails.getUsername());
        auditUser.setRole(userDetails.getRole());
        auditUser.setUuid(UUID.fromString(userDetails.getUserID()));
        auditUser.setFio(userDetails.getFullName());
        auditData.setUser(auditUser);
        String auditText = String.format(MESSAGE, userDetails.getUsername(), operationName, entityType.name());
        auditData.setText(auditText);

        this.auditClient.internalPost(auditData);
    }
}
