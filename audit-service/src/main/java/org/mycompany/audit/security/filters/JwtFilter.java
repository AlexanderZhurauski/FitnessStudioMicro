package org.mycompany.audit.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mycompany.audit.security.api.IExtendedUserDetails;
import org.mycompany.audit.security.api.ITokenHandler;
import org.mycompany.audit.web.clients.IUserClient;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isEmpty;

public class JwtFilter extends OncePerRequestFilter {

    private IUserClient userClient;
    private ITokenHandler tokenHandler;

    public JwtFilter(IUserClient userClient,
                     ITokenHandler tokenHandler) {
        this.userClient = userClient;
        this.tokenHandler = tokenHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
        if (!tokenHandler.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        IExtendedUserDetails userDetails = this.userClient.loadUserByUsername(tokenHandler.getUsername(token));

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                        List.of() : userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
