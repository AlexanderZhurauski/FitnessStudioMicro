package org.mycompany.product.config;

import jakarta.servlet.http.HttpServletResponse;
import org.mycompany.product.security.JWTProperty;
import org.mycompany.product.security.JwtTokenHandler;
import org.mycompany.product.security.api.ITokenHandler;
import org.mycompany.product.security.filters.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtFilter jwtFilter) throws Exception {
        http = http.cors().and().csrf().disable();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();


        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> response.setStatus(
                                HttpServletResponse.SC_UNAUTHORIZED
                        )
                )
                .accessDeniedHandler(
                        (request, response, ex) -> response.setStatus(
                                HttpServletResponse.SC_FORBIDDEN
                        )
                )
                .and();

        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/product").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/recipe").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/product/{uuid}/dt_update/{lastUpdated}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/recipe/{uuid}/dt_update/{lastUpdated}").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        return http.build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public ITokenHandler jwtTokenHandler(JWTProperty jwtProperty) {

        return new JwtTokenHandler(jwtProperty);
    }
}
