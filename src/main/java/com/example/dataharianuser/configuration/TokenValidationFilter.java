package com.example.dataharianuser.configuration;
import com.example.dataharianuser.exception.UnauthenticatedException;
import com.example.dataharianuser.service.utils.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenValidationFilter extends OncePerRequestFilter{
    @Autowired
    private Authenticator authenticator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException , UnauthenticatedException {
        // Your custom logic here. For example:
        String authToken = request.getHeader("Authorization");
        Integer userId = authenticator.getUserId(authToken);
        if (userId == null){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("Unauthenticated");
            return;
        }
        request.setAttribute("userId", userId);

        // if everything is ok, let the request continue to the next filter or the controller
        filterChain.doFilter(request, response);
    }
}
