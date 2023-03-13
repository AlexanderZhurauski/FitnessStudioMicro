package org.mycompany.mail.dao.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "conf_token", schema = "app")
public class ConfirmationToken {

    @Id
    private String email;
    private String token;

    public ConfirmationToken() {
    }

    public ConfirmationToken(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
