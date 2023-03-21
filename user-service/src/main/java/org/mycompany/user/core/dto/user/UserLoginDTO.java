package org.mycompany.user.core.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserLoginDTO {

    @Email(message = "Invalid email format!")
    @NotBlank(message = "The email cannot be blank!")
    @NotNull(message = "No email has been provided!")
    private String mail;
    @Size(min = 5, message = "A password must be at least 5 symbols long")
    @NotNull(message = "No password has been provided!")
    private String password;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
