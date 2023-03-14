package org.mycompany.user.core.dto.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsDTO implements UserDetails {
    private	String mail;
    private String password;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean nonLocked;
    private Collection<String> authorityList;

    public UserDetailsDTO() {
    }

    public UserDetailsDTO(String mail, String password, boolean enabled,
                          boolean accountNonExpired, boolean credentialsNonExpired,
                          boolean nonLocked, Collection<String> authorityList) {
        this.mail = mail;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.nonLocked = nonLocked;
        this.authorityList = authorityList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorityList
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public String getUsername() {
        return this.mail;
    }
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }
    @Override
    public boolean isAccountNonLocked() {
        return this.nonLocked;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isNonLocked() {
        return this.nonLocked;
    }

    public void setNonLocked(boolean nonLocked) {
        this.nonLocked = nonLocked;
    }

    public Collection<String> getAuthorityList() {
        return this.authorityList;
    }

    public void setAuthorityList(Collection<String> authorityList) {
        this.authorityList = authorityList;
    }
}