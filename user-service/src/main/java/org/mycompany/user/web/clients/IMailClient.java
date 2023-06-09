package org.mycompany.user.web.clients;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mail-service")
public interface IMailClient {

    @PostMapping("/sendConfirmation")
    void sendConfirmationEmail(@Email @NotNull @NotBlank @RequestParam String address);

    @GetMapping("/verify")
    boolean verifyEmail(@NotNull @NotBlank @RequestParam String code,
                                            @Email @NotNull @NotBlank @RequestParam String mail);
}
