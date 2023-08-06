package com.be.monolithic.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthorizationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {
        logger.info("BEGIN doFilterInternal()");

        String jwt = getJwtFromRequest(httpServletRequest);

        if (StringUtils.hasText(jwt)) {
            try {
                SecurityContextHolder.getContext().setAuthentication(
                        authenticationProvider.validateToken(jwt));
            } catch (RuntimeException e) {
                logger.error("Could not set user authentication in security context", e);
                SecurityContextHolder.clearContext();
                throw e;
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
        logger.info("END doFilterInternal()");
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
