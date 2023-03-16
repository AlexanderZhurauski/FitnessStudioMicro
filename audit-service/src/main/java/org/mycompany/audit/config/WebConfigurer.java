package org.mycompany.audit.config;

import org.mycompany.audit.web.clients.IUserClient;
import org.mycompany.audit.security.JwtTokenUtil;
import org.mycompany.audit.security.filters.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Bean
    public JwtFilter jwtFilter(IUserClient userClient, JwtTokenUtil tokenUtil) {
        return new JwtFilter(userClient, tokenUtil);
    }

}
