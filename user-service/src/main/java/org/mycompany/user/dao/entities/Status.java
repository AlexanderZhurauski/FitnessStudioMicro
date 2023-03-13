package org.mycompany.user.dao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.mycompany.user.core.dto.enums.UserStatus;

@Entity
@Table(schema = "app", name = "user_status")
public class Status {
    @Id
    @Positive(message = "Invalid ID provided: it must be positive!")
    private int id;
    @NotNull(message = "No user status has been provided!")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public Status() {
    }

    public Status(UserStatus status) {
        this.status = status;
        this.id = status.ordinal() + 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
