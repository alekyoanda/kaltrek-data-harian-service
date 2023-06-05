package com.example.dataharianuser.service.filter;

import com.example.dataharianuser.exception.UnauthenticatedException;
import com.example.dataharianuser.filter.TokenValidationFilter;
import com.example.dataharianuser.exception.UnauthenticatedException;
import com.example.dataharianuser.service.utils.Authenticator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenValidationFilterTest {
    @Mock
    private Authenticator authenticator;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private PrintWriter printWriter;

    @InjectMocks
    private TokenValidationFilter tokenValidationFilter;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testDoFilterInternal_ValidToken() throws ServletException, IOException, UnauthenticatedException {
        // Mocking the necessary objects and behavior
        String authToken = "Bearer valid-token";
        Integer userId = 1;
        when(request.getRequestURI()).thenReturn("/api/v1/data-harian");
        when(request.getHeader("Authorization")).thenReturn(authToken);
        when(authenticator.getUserId(authToken)).thenReturn(userId);

        // Call the doFilterInternal method
        tokenValidationFilter.doFilterInternal(request, response, filterChain);

        // Verify that the necessary methods were called
        verify(request, times(1)).getRequestURI();
        verify(request, times(1)).getHeader("Authorization");
        verify(authenticator, times(1)).getUserId(authToken);
        verify(request, times(1)).setAttribute("userId", userId);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_InvalidToken() throws ServletException, IOException, UnauthenticatedException {
        // Mocking the necessary objects and behavior
        String authToken = "invalid-token";
        when(request.getRequestURI()).thenReturn("/api/v1/data-harian");
        when(request.getHeader("Authorization")).thenReturn(authToken);
        when(response.getWriter()).thenReturn(printWriter);
        when(authenticator.getUserId(authToken)).thenReturn(null);

        // Call the doFilterInternal method
        tokenValidationFilter.doFilterInternal(request, response, filterChain);

        // Verify that the necessary methods were called
        verify(request, times(1)).getRequestURI();
        verify(request, times(1)).getHeader("Authorization");
        verify(authenticator, times(1)).getUserId(authToken);
        verify(request, never()).setAttribute("userId", null);
        verify(filterChain, never()).doFilter(request, response);
        verify(response, times(1)).setStatus(HttpStatus.UNAUTHORIZED.value());
        verify(response, times(1)).setContentType(MediaType.APPLICATION_JSON_VALUE);
        verify(response.getWriter(), times(1)).write("Unauthenticated");
    }

    // Add more test cases for other scenarios if needed
}
