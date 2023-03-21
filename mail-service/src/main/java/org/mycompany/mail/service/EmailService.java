package org.mycompany.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.mycompany.mail.config.MailProperty;
import org.mycompany.mail.core.exceptions.custom.EntityNotFoundException;
import org.mycompany.mail.core.exceptions.custom.NoValidTokenFound;
import org.mycompany.mail.dao.entities.ConfirmationToken;
import org.mycompany.mail.dao.repositories.IConfirmationTokenRepository;
import org.mycompany.mail.service.api.IEmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class EmailService implements IEmailService {

    private JavaMailSender mailSender;
    private JobScheduler jobScheduler;
    private IConfirmationTokenRepository tokenRepository;
    private MailProperty mailProperty;

    public EmailService(JavaMailSender mailSender, JobScheduler jobScheduler,
                        IConfirmationTokenRepository tokenRepository, MailProperty mailProperty) {

        this.mailSender = mailSender;
        this.jobScheduler = jobScheduler;
        this.tokenRepository = tokenRepository;
        this.mailProperty = mailProperty;
    }

    @Override
    @Transactional
    public void sendConfirmationEmail(String address) {

        String confirmationToken = UUID.randomUUID().toString();
        this.tokenRepository.save(new ConfirmationToken(address, confirmationToken));
        this.jobScheduler.enqueue(() -> sendConfirmationLinkAsync(address, confirmationToken));
    }

    @Override
    public boolean verifyEmail(String code, String mail) {

        ConfirmationToken actualToken = this.tokenRepository.findById(mail)
                .orElseThrow(() -> new EntityNotFoundException(mail, "confirmation token"));
        String actualTokenValue = actualToken.getToken();

        if (actualTokenValue == null || actualTokenValue.isBlank()) {
            throw new NoValidTokenFound("No valid token for email '" + mail + "'");
        }

        return actualTokenValue.equals(code);
    }

    @Job(name = "Confirmation Link", retries = 3)
    public void sendConfirmationLinkAsync(String address, String token) throws MessagingException {

        MimeMessage confirmationMail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(confirmationMail, true);

        helper.setFrom(mailProperty.getSender());
        helper.setTo(address);
        helper.setSubject(mailProperty.getSubject());
        String link = String.format(mailProperty.getLink(), token, address);
        String text = String.format(mailProperty.getText(), link);
        helper.setText(text);

        mailSender.send(confirmationMail);
    }
}
