package org.mycompany.user.core.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.*;
import org.mycompany.user.core.dto.enums.UserRole;
import org.mycompany.user.core.dto.enums.UserStatus;

@JsonPropertyOrder({
        "mail",
        "fio",
        "role",
        "status",
        "password"
})
public class UserCreateDTO {

    @Email(message = "Invalid email format!")
    @NotBlank(message = "The email cannot be blank!")
    @NotNull(message = "No email has been provided!")
    private String mail;
    @NotBlank(message = "The full name cannot be blank!")
    @NotNull(message = "No full name has been provided!")
    private String fullName;
    @NotNull(message = "No user role has been provided!")
    private UserRole role;
    @NotNull(message = "No user status has been provided!")
    private UserStatus status;
    @Size(min = 5, message = "A password must be at least 5 symbols long")
    @NotNull(message = "No password has been provided!")
    private String password;

    public UserCreateDTO() {
    }

    public UserCreateDTO(String mail, String fio, UserRole role,
                         UserStatus status, String password) {
        this.mail = mail;
        this.fullName = fio;
        this.role = role;
        this.status = status;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @JsonProperty("fio")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("fio")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
