package com.example.dataharianuser.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Adjust the mapping based on your API endpoints
                .allowedOrigins("http://localhost:3001", "https://kaltrek-project-adpro-c11.vercel.app") // Adjust the allowed origin to match your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Adjust the allowed HTTP methods based on your API
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}



