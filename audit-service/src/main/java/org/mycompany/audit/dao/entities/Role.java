package org.mycompany.audit.dao.entities;

import jakarta.persistence.*;
import org.mycompany.audit.core.dto.enums.UserRole;

@Entity
@Table(schema = "app", name = "user_role")
public class Role {
    @Id
    private int id;
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
