package com.example.Practice.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Practice.entity.User;
import com.example.Practice.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)

            throws ServletException, IOException {
                  String path = request.getServletPath();

    if (

            path.startsWith("/auth/")
            ||

            path.startsWith("/otp/")

    ) {

        filterChain.doFilter(request, response);

        return;
    }

        String authHeader =
                request.getHeader("Authorization");

        String token = null;

        String email = null;

        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);

            email = jwtService.extractEmail(token);
        }

        if (email != null &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

            User user =
                    userRepository
                            .findByEmail(email)
                            .orElse(null);

            if (user != null &&
                    jwtService.validateToken(
                            token,
                            user.getEmail()
                    )) {

            UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(
                user,
                null,
                java.util.List.of(
                        new org.springframework.security.core.authority.SimpleGrantedAuthority(
                                "ROLE_" + user.getRole()
                        )
                )
        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}