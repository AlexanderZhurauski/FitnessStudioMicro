package org.mycompany.report.config;

import org.mycompany.report.audit.advice.AuditAspect;
import org.mycompany.report.security.UserHolder;
import org.mycompany.report.web.clients.IAuditClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServiceConfig {

    @Bean
    public AuditAspect auditAspect(IAuditClient auditClient, UserHolder userHolder) {
        return new AuditAspect(auditClient, userHolder);
    }
}
