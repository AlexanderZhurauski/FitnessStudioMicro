package org.mycompany.audit.config;

import org.mycompany.audit.converters.json.StringToInstantConverter;
import org.mycompany.audit.web.clients.IUserClient;
import org.mycompany.audit.security.JwtTokenHandler;
import org.mycompany.audit.security.filters.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToInstantConverter());
    }
    @Bean
    public JwtFilter jwtFilter(IUserClient userClient, JwtTokenHandler tokenUtil) {
        return new JwtFilter(userClient, tokenUtil);
    }

}
