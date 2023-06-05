package com.example.dataharianuser.filter;
import com.example.dataharianuser.exception.UnauthenticatedException;
import com.example.dataharianuser.service.utils.Authenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import reactor.util.annotation.NonNull;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenValidationFilter extends OncePerRequestFilter{
    private final Authenticator authenticator;

    @Override
    public void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException , UnauthenticatedException {
        // Your custom logic here. For example:
        String path = request.getRequestURI();
        if (path.startsWith("/api/v1/data-harian")){
            String authToken = request.getHeader("Authorization");
            Integer userId = authenticator.getUserId(authToken);
            if (userId == null){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write("Unauthenticated");
                return;
            }
            request.setAttribute("userId", userId);

        }
        filterChain.doFilter(request, response);
    }
}
