package org.mycompany.mail.web.controllers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.mycompany.mail.service.api.IEmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public boolean verifyEmail(@NotNull @NotBlank @RequestParam String code,
                                                  @Email @NotNull @NotBlank @RequestParam String mail) {

        return this.emailService.verifyEmail(code, mail);
    }
}
