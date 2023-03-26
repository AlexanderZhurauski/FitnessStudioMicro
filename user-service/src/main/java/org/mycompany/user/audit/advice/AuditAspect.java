package org.mycompany.user.audit.advice;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.mycompany.user.audit.annotations.Audited;
import org.mycompany.user.audit.enums.EntityType;
import org.mycompany.user.audit.enums.OperationType;
import org.mycompany.user.config.KafkaConfig;
import org.mycompany.user.core.dto.audit.AuditDTO;
import org.mycompany.user.core.dto.audit.AuditUserDTO;
import org.mycompany.user.core.dto.enums.UserRole;
import org.mycompany.user.security.UserHolder;
import org.mycompany.user.security.api.IExtendedUserDetails;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;
import java.util.UUID;

@Aspect
public class AuditAspect {

    private static final String MESSAGE = "User '%s' has %s entity '%s'.";
    private static final UUID SYSTEM_UUID = UUID.fromString("b3451290-c64f-11ed-afa1-0242ac120002");

    private final UserHolder userHolder;
    private final KafkaTemplate<String, AuditDTO> kafkaTemplate;

    public AuditAspect(UserHolder userHolder,
                       KafkaTemplate<String, AuditDTO> kafkaTemplate) {
        this.userHolder = userHolder;
        this.kafkaTemplate = kafkaTemplate;
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
        Object tokenData = this.userHolder.getAuthentication().getPrincipal();
        if (entityType.equals(EntityType.USER) && tokenData instanceof String) {
            auditUser.setFio("SYSTEM");
            auditUser.setMail("admin@admin.com");
            auditUser.setUuid(SYSTEM_UUID);
            auditUser.setRole(UserRole.ADMIN);
            auditData.setUser(auditUser);
            String auditText = String.format(MESSAGE, "SYSTEM", operationName, entityType.name());
            auditData.setText(auditText);
            this.kafkaTemplate.send(KafkaConfig.AUDIT_TOPIC, auditData);
            return;
        }
        IExtendedUserDetails userDetails = (IExtendedUserDetails) tokenData;
        auditUser.setMail(userDetails.getUsername());
        auditUser.setRole(userDetails.getRole());
        auditUser.setUuid(UUID.fromString(userDetails.getUserID()));
        auditUser.setFio(userDetails.getFullName());
        auditData.setUser(auditUser);
        String auditText = String.format(MESSAGE, userDetails.getUsername(), operationName, entityType.name());
        auditData.setText(auditText);

        this.kafkaTemplate.send(KafkaConfig.AUDIT_TOPIC, auditData);
    }
}
