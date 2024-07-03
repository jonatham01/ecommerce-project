package com.auth_service.config.security;

import com.auth_service.Exception.ObjectNotFoundException;
import com.auth_service.entity.User;
import com.auth_service.service.JwtTokenSevice;
import com.auth_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.ServletException;


import java.io.IOException;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenSevice jwtTokenSevice;
    private final UserService userService;

    public JwtTokenAuthenticationFilter(JwtTokenSevice jwtTokenSevice, UserService userService) {
        this.jwtTokenSevice = jwtTokenSevice;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request,
                                    jakarta.servlet.http.HttpServletResponse response,
                                    jakarta.servlet.FilterChain filterChain)
            throws ServletException, IOException {

        String token  = jwtTokenSevice.getJwtFromRequest(request);

        if (token != null && jwtTokenSevice.validateToken(token)) {
            String username = jwtTokenSevice.getUsernameFromToken(token);
            User user = userService.findUserByUsername(username)
                    .orElseThrow(() -> new ObjectNotFoundException(username + " not found"));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities()
            );
            authentication.setDetails(new WebAuthenticationDetails(request));
            authentication.setAuthenticated(true);
            filterChain.doFilter(request, response);
        }
        filterChain.doFilter(request, response);

    }
}
