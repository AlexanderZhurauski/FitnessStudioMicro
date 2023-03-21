package org.mycompany.mail.config;

import org.jobrunr.scheduling.JobScheduler;
import org.mycompany.mail.dao.repositories.IConfirmationTokenRepository;
import org.mycompany.mail.service.EmailService;
import org.mycompany.mail.service.api.IEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;


@Configuration
public class ServiceConfig {
    @Bean
    public IEmailService emailService(JavaMailSender mailSender, JobScheduler jobScheduler,
                                      IConfirmationTokenRepository tokenRepository, MailProperty mailProperty) {
        return new EmailService(mailSender, jobScheduler, tokenRepository, mailProperty);
    }
}
