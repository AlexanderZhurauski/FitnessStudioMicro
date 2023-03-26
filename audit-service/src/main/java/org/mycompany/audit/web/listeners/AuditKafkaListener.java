package org.mycompany.audit.web.listeners;

import org.mycompany.audit.core.dto.AuditDTO;
import org.mycompany.audit.service.api.IAuditService;
import org.springframework.kafka.annotation.KafkaListener;

public class AuditKafkaListener {
    public static final String AUDIT_TOPIC = "audit_topic";
    private final IAuditService auditService;

    public AuditKafkaListener(IAuditService auditService) {
        this.auditService = auditService;
    }


    @KafkaListener(topics = AUDIT_TOPIC, groupId = "audit_group")
    public void processMessage(AuditDTO content) {
        this.auditService.post(content);
    }
}
