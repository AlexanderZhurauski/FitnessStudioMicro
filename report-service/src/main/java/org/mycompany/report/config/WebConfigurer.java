package org.mycompany.report.config;

import org.mycompany.report.security.api.ITokenHandler;
import org.mycompany.report.security.filters.JwtFilter;
import org.mycompany.report.web.clients.IUserClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Bean
    public JwtFilter jwtFilter(IUserClient userClient, ITokenHandler tokenHandler) {
        return new JwtFilter(userClient, tokenHandler);
    }
}
