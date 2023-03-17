package org.mycompany.product.web.clients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.mycompany.product.core.dto.user.UserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface IUserClient {

    @GetMapping("api/v1/users/getInternal")
    UserDetailsDTO loadUserByUsername(@NotNull @NotBlank @RequestParam String username);
}
