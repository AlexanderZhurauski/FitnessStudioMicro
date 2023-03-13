package org.mycompany.mail.web.controllers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.mycompany.mail.service.api.IEmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class EmailController {

    private IEmailService emailService;

    public EmailController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendConfirmation")
    public void sendConfirmationEmail(@Email @NotNull @NotBlank @RequestParam String address) {

        this.emailService.sendConfirmationEmail(address);
    }

    @GetMapping("/verify")
    public CompletableFuture<Boolean> verifyEmail(@Email @NotNull @NotBlank @RequestParam String mail,
                                                  @NotNull @NotBlank @RequestParam String code) {

        return CompletableFuture.completedFuture(this.emailService.verifyEmail(mail, code));
    }
}
