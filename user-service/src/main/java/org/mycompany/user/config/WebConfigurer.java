package org.mycompany.user.config;

import org.mycompany.user.converters.json.StringToInstantConverter;
import org.mycompany.user.security.JwtTokenHandler;
import org.mycompany.user.security.filters.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToInstantConverter());
    }
    @Bean
    public JwtFilter jwtFilter(UserDetailsService userService, JwtTokenHandler tokenHandler) {
        return new JwtFilter(userService, tokenHandler);
    }

}
