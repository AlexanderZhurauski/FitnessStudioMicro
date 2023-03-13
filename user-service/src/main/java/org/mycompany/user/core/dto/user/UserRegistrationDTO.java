package org.mycompany.user.core.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {

    @Email(message = "Invalid email format!")
    @NotBlank(message = "The email cannot be blank!")
    @NotNull(message = "No email has been provided!")
    private String mail;
    @NotBlank(message = "The full name cannot be blank!")
    @NotNull(message = "No full name has been provided!")
    private String fullName;
    @Size(min = 5, message = "A password must be at least 5 symbols long")
    @NotNull(message = "No password has been provided!")
    private String password;

    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(String mail, String fullName,
                               String password) {
        this.mail = mail;
        this.fullName = fullName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
