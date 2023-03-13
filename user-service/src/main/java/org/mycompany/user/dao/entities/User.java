package org.mycompany.user.dao.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import jakarta.persistence.*;
import org.mycompany.user.core.dto.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "app", name = "user")
public class User implements UserDetails {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private UUID uuid;
    @Column(name = "creation_time", nullable = false)
    private Instant creationTime = Instant.now();
    @Column(name = "last_updated", nullable = false)
    @Version
    private Instant lastUpdated = Instant.now();
    @Email(message = "Invalid email format!")
    @NotNull(message = "No email has been provided!")
    private	String mail;
    @NotBlank(message = "The password cannot be blank!")
    @NotNull(message = "No password has been provided!")
    private String password;
    @Column(name = "full_name")
    @NotBlank(message = "The full name cannot be blank!")
    @NotNull(message = "No full name has been provided!")
    private	String fullName;
    @ManyToOne
    @NotNull(message = "No user role has been provided!")
    private Role role;
    @ManyToOne
    @NotNull(message = "No user status has been provided!")
    private Status status;

    public User() {
    }

    public User(String mail, String password, String fullName,
                Role role, Status status) {
        this.mail = mail;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public Instant getCreationTime() {
        return creationTime;
    }
    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }
    public Instant getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority("ROLE_" + this.role.getRole().name()));
    }
    @Override
    public String getUsername() {
        return this.mail;
    }
    @Override
    public boolean isAccountNonExpired() {
        return !this.status.getStatus().equals(UserStatus.DEACTIVATED);
    }
    @Override
    public boolean isAccountNonLocked() {
        return !this.status.getStatus().equals(UserStatus.DEACTIVATED);
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return this.status.getStatus().equals(UserStatus.ACTIVATED);
    }
}
