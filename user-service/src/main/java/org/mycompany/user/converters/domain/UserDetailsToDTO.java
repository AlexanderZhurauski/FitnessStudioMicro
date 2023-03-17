package org.mycompany.user.converters.domain;

import org.mycompany.user.core.dto.user.UserDetailsDTO;
import org.mycompany.user.dao.entities.User;
import org.mycompany.user.security.api.IExtendedUserDetails;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;

public class UserDetailsToDTO implements Converter<User, IExtendedUserDetails> {
    @Override
    public UserDetailsDTO convert(User source) {

        UserDetailsDTO dto = new UserDetailsDTO();
        dto.setAuthorityList(source.getAuthorities().stream()
                .map(GrantedAuthority::toString)
                .toList());
        dto.setFullName(source.getFullName());
        dto.setUserID(source.getUuid());
        dto.setMail(source.getUsername());
        dto.setPassword(source.getPassword());
        dto.setEnabled(source.isEnabled());
        dto.setAccountNonLocked(source.isAccountNonLocked());
        dto.setAccountNonExpired(source.isAccountNonExpired());
        dto.setCredentialsNonExpired(source.isCredentialsNonExpired());
        dto.setRole(source.getRole().getRole());
        dto.setStatus(source.getStatus().getStatus());

        return dto;
    }
}
