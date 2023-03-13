package org.mycompany.user.dao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.mycompany.user.core.dto.enums.UserRole;

@Entity
@Table(schema = "app", name = "user_role")
public class Role {
    @Id
    @Positive(message = "Invalid ID provided: it must be positive!")
    private int id;
    @NotNull(message = "No user role has been provided!")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Role() {
    }

    public Role(UserRole role) {
        this.role = role;
        this.id = role.ordinal() + 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
