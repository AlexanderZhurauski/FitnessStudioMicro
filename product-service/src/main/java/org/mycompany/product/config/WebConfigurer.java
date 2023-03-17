package org.mycompany.product.config;

import org.mycompany.product.converters.json.StringToInstantConverter;
import org.mycompany.product.security.api.ITokenHandler;
import org.mycompany.product.security.filters.JwtFilter;
import org.mycompany.product.web.clients.IUserClient;
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
    public JwtFilter jwtFilter(IUserClient userClient, ITokenHandler tokenHandler) {
        return new JwtFilter(userClient, tokenHandler);
    }
}
