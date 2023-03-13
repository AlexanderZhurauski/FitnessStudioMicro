package org.mycompany.product.web.clients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.CompletableFuture;

@FeignClient(name = "user-service")
public interface IUserClient {

    @GetMapping("api/v1/users/getInternal")
    CompletableFuture<UserDetails> loadUserByUsername(@NotNull @NotBlank @RequestParam String username);
}
