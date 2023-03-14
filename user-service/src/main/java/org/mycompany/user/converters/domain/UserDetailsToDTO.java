package org.mycompany.user.converters.domain;

import org.mycompany.user.core.dto.user.UserDetailsDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsToDTO implements Converter<UserDetails, UserDetailsDTO> {
    @Override
    public UserDetailsDTO convert(UserDetails source) {

        UserDetailsDTO dto = new UserDetailsDTO();
        dto.setAuthorityList(source.getAuthorities().stream()
                .map(authority -> (SimpleGrantedAuthority) authority)
                .toList());
        dto.setMail(source.getUsername());
        dto.setPassword(source.getPassword());
        dto.setEnabled(source.isEnabled());
        dto.setNonLocked(source.isAccountNonLocked());
        dto.setAccountNonExpired(source.isAccountNonExpired());
        dto.setCredentialsNonExpired(source.isCredentialsNonExpired());

        return dto;
    }
}
